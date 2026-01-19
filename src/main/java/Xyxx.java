public class Xyxx {
    public static void main(String[] args) {
        sendMessage(makeGreetMessage());
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
        System.out.println(message);
        System.out.println("____________________________________________________________\n");
    }
}
