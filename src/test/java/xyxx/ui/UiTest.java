package xyxx.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class UiTest {

    @Test
    public void sendMessageTest() {
        UiSettings s = UiSettings.builder().setMessageWidth(3).setIndent(2).build();
        UiAdapter ui = new CliUi(s);

        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            ui.sendMessage("hello");

            String out = baos.toString();
            assertTrue(out.contains("hello"));
            assertTrue(out.contains("___"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void sendGreetMessageTest() {
        UiAdapter ui = new CliUi();

        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            ui.sendGreetMessage();

            String out = baos.toString();
            assertTrue(out.contains("Hello from"));
            assertTrue(out.contains("How may I help you today?"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void sendExitMessageTest() {
        UiAdapter ui = new CliUi();

        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            ui.sendExitMessage();

            String out = baos.toString();
            assertTrue(out.contains("See you soon, bye!"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void getInputTest() throws IOException {
        String inputLine = "user input\n";
        ByteArrayInputStream in = new ByteArrayInputStream(inputLine.getBytes());

        java.io.InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        try {
            System.setIn(in);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            UiAdapter ui = new CliUi(); // scanner is created at construction and binds to System.in
            String line = ui.receiveInput();

            assertEquals("user input", line);
            assertTrue(baos.toString().contains("> "));
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
