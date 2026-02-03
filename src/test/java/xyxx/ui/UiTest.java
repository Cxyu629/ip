package xyxx.ui;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiTest {

    @Test
    public void printMessageTest() {
        UiSettings s = UiSettings.builder().setMessageWidth(3).setIndent(2).build();
        Ui ui = new Ui(s);

        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            ui.printMessage("hello");

            String out = baos.toString();
            assertTrue(out.contains("hello"));
            assertTrue(out.contains("___"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void printGreetMessageTest() {
        Ui ui = new Ui();

        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            ui.printGreetMessage();

            String out = baos.toString();
            assertTrue(out.contains("Hello from"));
            assertTrue(out.contains("How may I help you today?"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void printExitMessageTest() {
        Ui ui = new Ui();

        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            ui.printExitMessage();

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

            Ui ui = new Ui(); // scanner is created at construction and binds to System.in
            String line = ui.getInput();

            assertEquals("user input", line);
            assertTrue(baos.toString().contains("> "));
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
