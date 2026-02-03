import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import command.CommandDefinition;
import command.ParamDefinition;
import datetime.PartialDateTime;
import storage.Storage;
import task.DeadlineTask;
import task.EventTask;
import task.Task;
import task.TaskList;
import task.TodoTask;
import ui.Ui;
import parser.ParsedCommand;
import parser.Parser;

public class Xyxx {
    enum TaskAction {
        MARK,
        UNMARK,
        DELETE,
    }

    public static void main(String[] args) {
        new Xyxx().run();
    }

    private static final Map<String, CommandDefinition> COMMANDS = Map.of(
            "list", new CommandDefinition("list", false, List.of()),
            "todo", new CommandDefinition("todo", true, List.of()),
            "deadline",
            new CommandDefinition("deadline", true,
                    List.of(new ParamDefinition("by", true, ParamDefinition.Type.PARTIALDATETIME))),
            "event",
            new CommandDefinition("event", true,
                    List.of(new ParamDefinition("from", true, ParamDefinition.Type.PARTIALDATETIME),
                            new ParamDefinition("to", true, ParamDefinition.Type.PARTIALDATETIME))),
            "mark", new CommandDefinition("mark", true, List.of()),
            "unmark", new CommandDefinition("unmark", true, List.of()),
            "delete", new CommandDefinition("delete", true, List.of()));

    private TaskList tasks;

    private Ui ui = new Ui();

    private Parser parser = new Parser(COMMANDS);

    public void run() {
        try {
            tasks = Storage.load();
        } catch (IOException e) {
            ui.printMessage(String.format("Oh no! %s", e));
            return;
        }

        ui.printGreetMessage();

        while (true) {
            String input = ui.getInput();

            if (input.toLowerCase().equals("bye"))
                break;

            processInput(input);
        }

        try {
            Storage.save(tasks);
        } catch (IOException e) {
            ui.printMessage(String.format("Oh no! %s", e));
            return;
        }

        ui.printExitMessage();
    }

    private void processInput(String input) {
        try {
            ParsedCommand parsed = parser.parse(input);

            switch (parsed.commandName()) {
                case "":
                    if (parsed.subject().equals(""))
                        ui.printMessage("Oh, remaining silent aren't we?");
                    else
                        ui.printMessage("You said: " + input);
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
                default:
                    break;
            }
        } catch (ParseException e) {
            ui.printMessage(String.format("Oop! %s", e.getMessage()));
        }
    }

    private void handleTodoCommand(String subject) {
        if (subject.equals("")) {
            ui.printMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        TodoTask todo = new TodoTask(subject);
        tasks.add(todo);
        ui.printMessage("Added todo: " + todo);
    }

    private void handleDeadlineCommand(String subject, Map<String, String> params) {
        if (subject.equals("")) {
            ui.printMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        String description = subject;
        String byString = params.get("by");
        PartialDateTime by = PartialDateTime.fromString(byString);

        DeadlineTask deadline = new DeadlineTask(description, by);
        tasks.add(deadline);
        ui.printMessage("Added deadline: " + deadline);
    }

    private void handleEventCommand(String subject, Map<String, String> params) {
        if (subject.equals("")) {
            ui.printMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        String description = subject;
        String fromString = params.get("from");
        String toString = params.get("to");
        PartialDateTime from = PartialDateTime.fromString(fromString);
        PartialDateTime to = PartialDateTime.fromString(toString);

        EventTask event = new EventTask(description, from, to);
        tasks.add(event);
        ui.printMessage("Added event: " + event);
    }

    private void handleTaskActionCommand(String subject, TaskAction action) {
        try {
            int taskNumber = Integer.parseInt(subject);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                ui.printMessage(CommandFailureMessage.taskIndexOutOfRange(taskNumber));
                return;
            }

            Task currentTask = tasks.get(taskNumber - 1);
            switch (action) {
                case MARK:
                    currentTask.markAsDone();
                    ui.printMessage(String.format("Alright, I have it marked!\n     %s", currentTask));
                    break;
                case UNMARK:
                    currentTask.unmarkAsDone();
                    ui.printMessage(String.format("Alright, I have it unmarked!\n     %s", currentTask));
                    break;
                case DELETE:
                    tasks.remove(taskNumber - 1);
                    ui.printMessage(String.format("Alright, I have it deleted!\n     %s", currentTask));
                    break;
            }
        } catch (NumberFormatException e) {
            ui.printMessage(CommandFailureMessage.invalidTaskNumber(subject));
        }
    }

    private void handleListCommand() {
        if (tasks.size() == 0) {
            ui.printMessage("There's nothing here -_-");
            return;
        }

        String message = "Let's do this!\n";
        ui.printMessage(message + tasks.toString());
    }
}