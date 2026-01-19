import java.util.Scanner;

public class Xyxx {
    public static void main(String[] args) {
        sendMessage(makeGreetMessage());

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine();

                if (input.toLowerCase().equals("bye"))
                    break;
                else
                    sendMessage(input);
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
