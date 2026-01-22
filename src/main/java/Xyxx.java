import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Xyxx {
    static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        sendMessage(makeGreetMessage());

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine().strip();

                if (input.toLowerCase().equals("bye"))
                    break;

                processInput(input);
            }
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
                handleMarkCommand(argument);
                break;
            case "unmark":
                handleUnmarkCommand(argument);
                break;
            default:
                sendMessage("You said: " + input);
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
        if (argument.equals("")) {
            sendMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        Pattern deadlinePattern = Pattern.compile("(.+?)\\s+\\/by\\s+(.+)");
        Matcher matcher = deadlinePattern.matcher(argument);
        if (!matcher.matches()) {
            sendMessage(CommandFailureMessage.invalidFormat("deadline <description> /by <due datetime>"));
            return;
        }

        String description = matcher.group(1);
        String by = matcher.group(2);
        DeadlineTask deadline = new DeadlineTask(description, by);
        tasks.add(deadline);
        sendMessage("Added deadline: " + deadline);
    }

    static void handleEventCommand(String argument) {
        if (argument.equals("")) {
            sendMessage(CommandFailureMessage.emptyTaskDescription());
            return;
        }

        Pattern deadlinePattern = Pattern.compile("(.+?)\\s+\\/from\\s+(.+?)\\s+\\/to\\s+(.+?)");
        Matcher matcher = deadlinePattern.matcher(argument);
        if (!matcher.matches()) {
            sendMessage(CommandFailureMessage
                    .invalidFormat("event <description> /from <from datetime> /to <to datetime>"));
            return;
        }

        String description = matcher.group(1);
        String from = matcher.group(2);
        String to = matcher.group(3);
        EventTask event = new EventTask(description, from, to);
        tasks.add(event);
        sendMessage("Added event: " + event);

    }

    static void handleMarkCommand(String argument) {
        try {
            int taskNumber = Integer.parseInt(argument);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                sendMessage(CommandFailureMessage.taskIndexOutOfRange(taskNumber));
                return;
            }

            Task currentTask = tasks.get(taskNumber - 1);
            currentTask.markAsDone();
            sendMessage(String.format("Alright, I have it marked!\n     %s", currentTask));
        } catch (NumberFormatException e) {
            sendMessage(CommandFailureMessage.invalidTaskNumber(argument));
        }
    }

    static void handleUnmarkCommand(String argument) {
        try {
            int taskNumber = Integer.parseInt(argument);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                sendMessage(CommandFailureMessage.taskIndexOutOfRange(taskNumber));
                return;
            }

            Task currentTask = tasks.get(taskNumber - 1);
            currentTask.unmarkAsDone();
            sendMessage(String.format("Alright, I have it unmarked!\n     %s", currentTask));
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