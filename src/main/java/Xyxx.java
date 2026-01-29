import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datetime.PartialDateTime;
import task.DeadlineTask;
import task.EventTask;
import task.Task;
import task.TodoTask;

public class Xyxx {
    enum TaskAction {
        MARK,
        UNMARK,
        DELETE,
    }

    static ArrayList<Task> tasks;

    public static void main(String[] args) {
        try {
            tasks = TasksStorage.load();
        } catch (IOException e) {
            sendMessage(String.format("Oh no! %s", e));
            return;
        }

        sendMessage(makeGreetMessage());

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine().strip();

                if (input.toLowerCase().equals("bye"))
                    break;

                processInput(input);
            }
        }

        try {
            TasksStorage.save(tasks);
        } catch (IOException e) {
            sendMessage(String.format("Oh no! %s", e));
            return;
        }

        sendMessage(makeExitMessage());
    }

    static void processInput(String input) {
        Pattern splitPattern = Pattern.compile("(\\s+)");

        String[] splitResult = splitPattern.split(input, 2);
        String command = "", argument = "";
        if (splitResult.length > 0) {
            command = splitResult[0].toLowerCase();
        }
        if (splitResult.length > 1) {
            argument = splitResult[1];
        }

        switch (command) {
            case "":
                sendMessage("Oh, remaining silent aren't we?");
                break;
            case "list":
                handleListCommand();
                break;
            case "todo":
                handleTodoCommand(argument);
                break;
            case "deadline":
                handleDeadlineCommand(argument);
                break;
            case "event":
                handleEventCommand(argument);
                break;
            case "mark":
                handleTaskActionCommand(argument, TaskAction.MARK);
                break;
            case "unmark":
                handleTaskActionCommand(argument, TaskAction.UNMARK);
                break;
            case "delete":
                handleTaskActionCommand(argument, TaskAction.DELETE);
                break;
            default:
                sendMessage("You said: " + input);
                break;
        }
    }

    static void handleTodoCommand(String argument) {
        if (argument.equals("")) {
            sendMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        TodoTask todo = new TodoTask(argument);
        tasks.add(todo);
        sendMessage("Added todo: " + todo);
    }

    static void handleDeadlineCommand(String argument) {
        final String INVALID_FORMAT_MESSAGE = CommandFailureMessage
                .invalidFormat(
                        String.format("deadline <description> /by %s", PartialDateTime.FORMAT_HINT));

        if (argument.equals("")) {
            sendMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        Pattern deadlinePattern = Pattern.compile("(.+?)\\s+\\/by\\s+(.+)");
        Matcher matcher = deadlinePattern.matcher(argument);
        if (!matcher.matches()) {
            sendMessage(INVALID_FORMAT_MESSAGE);
            return;
        }

        String description = matcher.group(1);
        String byString = matcher.group(2);
        PartialDateTime by = PartialDateTime.fromString(byString);
        if (by == null) {
            sendMessage(INVALID_FORMAT_MESSAGE);
            return;
        }

        DeadlineTask deadline = new DeadlineTask(description, by);
        tasks.add(deadline);
        sendMessage("Added deadline: " + deadline);
    }

    static void handleEventCommand(String argument) {
        final String INVALID_FORMAT_MESSAGE = CommandFailureMessage
                .invalidFormat(
                        String.format("event <description> /from %s /to %s", PartialDateTime.FORMAT_HINT,
                                PartialDateTime.FORMAT_HINT));

        if (argument.equals("")) {
            sendMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        Pattern deadlinePattern = Pattern.compile("(.+?)\\s+\\/from\\s+(.+?)\\s+\\/to\\s+(.+?)");
        Matcher matcher = deadlinePattern.matcher(argument);
        if (!matcher.matches()) {
            sendMessage(INVALID_FORMAT_MESSAGE);
            return;
        }

        String description = matcher.group(1);
        String fromString = matcher.group(2);
        String toString = matcher.group(3);
        PartialDateTime from = PartialDateTime.fromString(fromString);
        PartialDateTime to = PartialDateTime.fromString(toString);
        if (from == null || to == null) {
            sendMessage(INVALID_FORMAT_MESSAGE);
            return;
        }

        EventTask event = new EventTask(description, from, to);
        tasks.add(event);
        sendMessage("Added event: " + event);

    }

    static void handleTaskActionCommand(String argument, TaskAction action) {
        try {
            int taskNumber = Integer.parseInt(argument);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                sendMessage(CommandFailureMessage.taskIndexOutOfRange(taskNumber));
                return;
            }

            Task currentTask = tasks.get(taskNumber - 1);
            switch (action) {
                case MARK:
                    currentTask.markAsDone();
                    sendMessage(String.format("Alright, I have it marked!\n     %s", currentTask));
                    break;
                case UNMARK:
                    currentTask.unmarkAsDone();
                    sendMessage(String.format("Alright, I have it unmarked!\n     %s", currentTask));
                    break;
                case DELETE:
                    tasks.remove(taskNumber - 1);
                    sendMessage(String.format("Alright, I have it deleted!\n     %s", currentTask));
                    break;
            }
        } catch (NumberFormatException e) {
            sendMessage(CommandFailureMessage.invalidTaskNumber(argument));
        }
    }

    static void handleListCommand() {
        if (tasks.isEmpty()) {
            sendMessage("There's nothing here -_-");
            return;
        }

        String message = "Let's do this!\n";
        for (int i = 0; i < tasks.size(); i++) {
            message += String.format("% 3d. %s\n", (i + 1), tasks.get(i));
        }
        sendMessage(message.strip());
    }

    static String makeGreetMessage() {
        String logo = """
                 \\o       o/
                  v\\     /v
                   <\\   />
                     \\o/    o      o   \\o    o/  \\o    o/
                      |    <|>    <|>   v\\  /v    v\\  /v
                     / \\   < >    < >    <\\/>      <\\/>
                   o/   \\o  \\o    o/     o/\\o      o/\\o
                  /v     v\\  v\\  /v     /v  v\\    /v  v\\
                 />       <\\  <\\/>     />    <\\  />    <\\
                               /
                              o
                           __/>
                """;

        return "Hello from \n \n" + logo + "\nHow may I help you today?";
    }

    static String makeExitMessage() {
        return "See you soon, bye!";
    }

    static void sendMessage(String message) {
        String delimiter = "____________________________________________________________\n";
        String indent = " ".repeat(8);
        System.out.println(indent + delimiter);
        System.out.println(indent + message.replace("\n", "\n" + indent));
        System.out.println(indent + delimiter);
    }
}