package xyxx;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import xyxx.command.CommandDefinition;
import xyxx.command.ParamDefinition;
import xyxx.datetime.PartialDateTime;
import xyxx.parser.ParsedCommand;
import xyxx.parser.Parser;
import xyxx.storage.Storage;
import xyxx.task.DeadlineTask;
import xyxx.task.EventTask;
import xyxx.task.Task;
import xyxx.task.TaskList;
import xyxx.task.TodoTask;
import xyxx.ui.CliUi;
import xyxx.ui.UiAdapter;

/**
 * Main application entry point that wires together the UI, parser and storage components and
 * implements command handling logic.
 */
public class Xyxx {
    enum TaskAction {
        MARK, UNMARK, DELETE,
    }

    private static final Map<String, CommandDefinition> COMMANDS = Map.of("list",
            new CommandDefinition("list", false, List.of()), "todo",
            new CommandDefinition("todo", true, List.of()), "deadline",
            new CommandDefinition("deadline", true,
                    List.of(new ParamDefinition("by", true, ParamDefinition.Type.PARTIALDATETIME))),
            "event",
            new CommandDefinition("event", true,
                    List.of(new ParamDefinition("from", true, ParamDefinition.Type.PARTIALDATETIME),
                            new ParamDefinition("to", true, ParamDefinition.Type.PARTIALDATETIME))),
            "mark", new CommandDefinition("mark", true, List.of()), "unmark",
            new CommandDefinition("unmark", true, List.of()), "delete",
            new CommandDefinition("delete", true, List.of()), "find",
            new CommandDefinition("find", true, List.of()));

    private TaskList tasks;

    private UiAdapter ui = new CliUi();

    private Parser parser = new Parser(COMMANDS);

    /**
     * Application entry point.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Xyxx().run();
    }

    /**
     * Runs the main read-eval-print loop: greets the user, reads input until "bye" is entered,
     * processes commands and persists tasks on exit.
     */
    public void run() {
        try {
            tasks = Storage.load();
        } catch (IOException e) {
            ui.sendMessage(String.format("Oh no! %s", e));
            return;
        }

        ui.sendGreetMessage();

        while (true) {
            String input = ui.receiveInput();

            if (input.toLowerCase().equals("bye")) {
                break;
            }

            processInput(input);
        }

        try {
            Storage.save(tasks);
        } catch (IOException e) {
            ui.sendMessage(String.format("Oh no! %s", e));
            return;
        }

        ui.sendExitMessage();
    }

    private void processInput(String input) {
        try {
            ParsedCommand parsed = parser.parse(input);

            switch (parsed.commandName()) {
            case "":
                if (parsed.subject().equals("")) {
                    ui.sendMessage("Oh, remaining silent aren't we?");
                } else {
                    ui.sendMessage("You said: " + input);
                }
                break;
            case "list":
                handleListCommand();
                break;
            case "todo":
                handleTodoCommand(parsed.subject());
                break;
            case "deadline":
                handleDeadlineCommand(parsed.subject(), parsed.params());
                break;
            case "event":
                handleEventCommand(parsed.subject(), parsed.params());
                break;
            case "mark":
                handleTaskActionCommand(parsed.subject(), TaskAction.MARK);
                break;
            case "unmark":
                handleTaskActionCommand(parsed.subject(), TaskAction.UNMARK);
                break;
            case "delete":
                handleTaskActionCommand(parsed.subject(), TaskAction.DELETE);
                break;
            case "find":
                handleFindCommand(parsed.subject());
                break;
            default:
                break;
            }
        } catch (ParseException e) {
            ui.sendMessage(String.format("Oop! %s", e.getMessage()));
        }
    }

    private void handleTodoCommand(String subject) {
        if (subject.equals("")) {
            ui.sendMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        TodoTask todo = new TodoTask(subject);
        tasks.add(todo);
        ui.sendMessage("Added todo: " + todo);
    }

    private void handleDeadlineCommand(String subject, Map<String, String> params) {
        if (subject.equals("")) {
            ui.sendMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        String description = subject;
        String byString = params.get("by");
        PartialDateTime by = PartialDateTime.fromString(byString);

        DeadlineTask deadline = new DeadlineTask(description, by);
        tasks.add(deadline);
        ui.sendMessage("Added deadline: " + deadline);
    }

    private void handleEventCommand(String subject, Map<String, String> params) {
        if (subject.equals("")) {
            ui.sendMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        String description = subject;
        String fromString = params.get("from");
        String toString = params.get("to");
        PartialDateTime from = PartialDateTime.fromString(fromString);
        PartialDateTime to = PartialDateTime.fromString(toString);

        EventTask event = new EventTask(description, from, to);
        tasks.add(event);
        ui.sendMessage("Added event: " + event);
    }

    private void handleTaskActionCommand(String subject, TaskAction action) {
        try {
            int taskNumber = Integer.parseInt(subject);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                ui.sendMessage(CommandFailureMessage.taskIndexOutOfRange(taskNumber));
                return;
            }

            Task currentTask = tasks.get(taskNumber - 1);
            switch (action) {
            case MARK:
                currentTask.markAsDone();
                ui.sendMessage(String.format("Alright, I have it marked!\n     %s", currentTask));
                break;
            case UNMARK:
                currentTask.unmarkAsDone();
                ui.sendMessage(
                        String.format("Alright, I have it unmarked!\n     %s", currentTask));
                break;
            case DELETE:
                tasks.remove(taskNumber - 1);
                ui.sendMessage(String.format("Alright, I have it deleted!\n     %s", currentTask));
                break;
            default:
                throw new UnsupportedOperationException("Unsupported task action: " + action);
            }
        } catch (NumberFormatException e) {
            ui.sendMessage(CommandFailureMessage.invalidTaskNumber(subject));
        }
    }

    private void handleFindCommand(String subject) {
        if (subject.isBlank()) {
            ui.sendMessage(CommandFailureMessage.invalidFormat("find <query>"));
            return;
        }

        TaskList found = tasks.filterByKeyword(subject.strip());

        if (found.size() == 0) {
            ui.sendMessage(String.format("No tasks found matching \"%s\"", subject));
            return;
        }

        ui.sendMessage("Found tasks:\n" + found.toString());
    }

    private void handleListCommand() {
        if (tasks.size() == 0) {
            ui.sendMessage("There's nothing here -_-");
            return;
        }

        String message = "Let's do this!\n";
        ui.sendMessage(message + tasks.toString());
    }
}
