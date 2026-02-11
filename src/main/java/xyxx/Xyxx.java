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

    private Parser parser = new Parser(COMMANDS);

    /**
     * Initializes the Xyxx application by loading tasks from storage.
     */
    public void init() {
        try {
            tasks = Storage.load();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Handles user input, parses commands, and executes corresponding actions.
     *
     * @param input the user input string
     * @return the {@link Result} of command execution
     */
    public Result handleInput(String input) {
        if (input.equalsIgnoreCase("bye")) {
            return new Result(false, "", false);
        }
        try {
            ParsedCommand parsed = parser.parse(input);

            switch (parsed.commandName()) {
            case "":
                if (parsed.subject().equals("")) {
                    return new Result(true, "Oh, remaining silent aren't we?", false);
                } else {
                    return new Result(true, "You said: " + input, false);
                }
            case "list":
                return handleListCommand();
            case "todo":
                return handleTodoCommand(parsed.subject());
            case "deadline":
                return handleDeadlineCommand(parsed.subject(), parsed.params());
            case "event":
                return handleEventCommand(parsed.subject(), parsed.params());
            case "mark":
                return handleTaskActionCommand(parsed.subject(), TaskAction.MARK);
            case "unmark":
                return handleTaskActionCommand(parsed.subject(), TaskAction.UNMARK);
            case "delete":
                return handleTaskActionCommand(parsed.subject(), TaskAction.DELETE);
            case "find":
                return handleFindCommand(parsed.subject());
            default:
                return new Result(true, "I don't know what that means.", true);
            }
        } catch (ParseException e) {
            return new Result(true, CommandFailureMessage.parseError(e.getMessage()), true);
        }
    }

    /**
     * Saves tasks to storage when closing the application.
     */
    public void close() {
        try {
            Storage.save(tasks);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private Result handleTodoCommand(String subject) {
        if (subject.equals("")) {
            return new Result(true, CommandFailureMessage.emptyTaskDescription(), true);
        }

        TodoTask todo = new TodoTask(subject);
        tasks.add(todo);
        return new Result(true, "Added todo: " + todo, false);
    }

    private Result handleDeadlineCommand(String subject, Map<String, String> params) {
        if (subject.equals("")) {
            return new Result(true, CommandFailureMessage.emptyTaskDescription(), true);
        }

        String description = subject;
        String byString = params.get("by");
        PartialDateTime by = PartialDateTime.fromString(byString);

        DeadlineTask deadline = new DeadlineTask(description, by);
        tasks.add(deadline);
        return new Result(true, "Added deadline: " + deadline, false);
    }

    private Result handleEventCommand(String subject, Map<String, String> params) {
        if (subject.equals("")) {
            return new Result(true, CommandFailureMessage.emptyTaskDescription(), true);
        }

        String description = subject;
        String fromString = params.get("from");
        String toString = params.get("to");
        PartialDateTime from = PartialDateTime.fromString(fromString);
        PartialDateTime to = PartialDateTime.fromString(toString);

        EventTask event = new EventTask(description, from, to);
        tasks.add(event);
        return new Result(true, "Added event: " + event, false);
    }

    private Result handleTaskActionCommand(String subject, TaskAction action) {
        try {
            int taskNumber = Integer.parseInt(subject);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                return new Result(true, CommandFailureMessage.taskIndexOutOfRange(taskNumber),
                        true);
            }

            Task currentTask = tasks.get(taskNumber - 1);
            switch (action) {
            case MARK:
                currentTask.markAsDone();
                return new Result(true,
                        String.format("Alright, I have it marked!\n     %s", currentTask), false);
            case UNMARK:
                currentTask.unmarkAsDone();
                return new Result(true,
                        String.format("Alright, I have it unmarked!\n     %s", currentTask), false);
            case DELETE:
                tasks.remove(taskNumber - 1);
                return new Result(true,
                        String.format("Alright, I have it deleted!\n     %s", currentTask), false);
            default:
                assert false : "Unhandled TaskAction: " + action;
                throw new UnsupportedOperationException("Unsupported task action: " + action);
            }
        } catch (NumberFormatException e) {
            return new Result(true, CommandFailureMessage.invalidTaskNumber(subject), true);
        }
    }

    private Result handleFindCommand(String subject) {
        if (subject.isBlank()) {
            return new Result(true, CommandFailureMessage.invalidFormat("find <query>"), true);
        }

        TaskList found = tasks.filterByKeyword(subject.strip());

        if (found.size() == 0) {
            return new Result(true, String.format("No tasks found matching \"%s\"", subject),
                    false);
        }

        return new Result(true, "Found tasks:\n" + found.toString(), false);
    }

    private Result handleListCommand() {
        if (tasks.size() == 0) {
            return new Result(true, "There's nothing here -_-", false);
        }

        String message = "Let's do this!\n";
        return new Result(true, message + tasks.toString(), false);
    }
}
