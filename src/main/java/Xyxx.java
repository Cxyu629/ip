import java.util.Scanner;
import java.util.regex.Pattern;

public class Xyxx {
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
                    firstWord = splitResult[0];
                }

                switch (firstWord) {
                    case "":
                        sendMessage("Oh, remaining silent are we?");
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

    public static String makeGreetMessage() {
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

    public static String makeExitMessage() {
        return "See you soon, bye!";
    }

    public static void sendMessage(String message) {
        String delimiter = "____________________________________________________________\n";
        String indent = " ".repeat(8);
        System.out.println(indent + delimiter);
        System.out.println(indent + message.replace("\n", "\n" + indent));
        System.out.println(indent + delimiter);
    }

}
