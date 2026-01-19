import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Xyxx {
    static ArrayList<String> items = new ArrayList<>();

    public static void main(String[] args) {
        sendMessage(makeGreetMessage());

        try (Scanner scanner = new Scanner(System.in)) {
            boolean exited = false;
            while (!exited) {
                String input = scanner.nextLine().strip();

                Pattern splitPattern = Pattern.compile("(\\s+)");

                String[] splitResult = splitPattern.split(input, 2);
                String firstWord;
                if (splitResult.length == 0) {
                    firstWord = "";
                } else {
                    firstWord = splitResult[0].toLowerCase();
                }

                switch (firstWord) {
                    case "":
                        sendMessage("Oh, remaining silent aren't we?");
                        break;
                    case "list":
                        sendMessage(makeItemListMessage());
                        break;
                    case "bye":
                        exited = true;
                        break;
                    default:
                        sendMessage(input);
                }
            }
        }

        sendMessage(makeExitMessage());
    }
    
    static String makeItemListMessage() {
        if (items.isEmpty()) {
            return "There's nothing here -_-";
        }
        String message = "";
        for (int i = 0; i < items.size(); i++) {
            message += (i + 1) + ". " + items.get(i);
        }
        return message;
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
