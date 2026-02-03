package xyxx.parser;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyxx.command.CommandDefinition;
import xyxx.command.ParamDefinition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    private Parser makeParserWithAddCommand() {
        ParamDefinition n = new ParamDefinition("n", true, ParamDefinition.Type.NUMBER);
        ParamDefinition t = new ParamDefinition("t", false, ParamDefinition.Type.STRING);
        ParamDefinition d = new ParamDefinition("d", false, ParamDefinition.Type.PARTIALDATETIME);
        CommandDefinition addDef = new CommandDefinition("add", true, List.of(n, t, d));
        Map<String, CommandDefinition> reg = new HashMap<>();
        reg.put("add", addDef);
        return new Parser(reg);
    }

    @Test
    public void parseSuccessTest() throws ParseException {
        Parser parser = makeParserWithAddCommand();
        ParsedCommand pc = parser.parse("add MySubject /n 42 /t some title /d 2020-01-02 1530");

        assertEquals("add", pc.commandName());
        assertEquals("MySubject", pc.subject());
        assertEquals("42", pc.params().get("n"));
        assertEquals("some title", pc.params().get("t"));
        assertEquals("2020-01-02 1530", pc.params().get("d"));
    }

    @Test
    public void missingRequiredParamTest() {
        Parser parser = makeParserWithAddCommand();
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse("add X /t something"));
        assertTrue(ex.getMessage().contains("Missing required parameter: n"));
    }

    @Test
    public void unexpectedParamTest() {
        Parser parser = makeParserWithAddCommand();
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse("add X /x 1"));
        assertTrue(ex.getMessage().contains("Unexpected parameter: x"));
    }

    @Test
    public void invalidNumberParamTest() {
        Parser parser = makeParserWithAddCommand();
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse("add X /n notanumber"));
        assertTrue(ex.getMessage().contains("Invalid value for number parameter"));
    }

    @Test
    public void invalidDateTimeParamTest() {
        Parser parser = makeParserWithAddCommand();
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse("add X /n 5 /d notadate"));
        assertTrue(ex.getMessage().contains("Invalid value for datetime parameter"));
    }

    @Test
    public void invalidParamFormatTest() {
        Parser parser = makeParserWithAddCommand();
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse("add X /n 3 /t? notvalid"));
        assertTrue(ex.getMessage().contains("Invalid parameter format"));
    }

    @Test
    public void unknownCommandReturnsEmptyTest() throws ParseException {
        Parser parser = makeParserWithAddCommand();
        ParsedCommand pc = parser.parse("unknown whatever");
        assertEquals("", pc.commandName());
        assertEquals("unknown whatever", pc.subject());
        assertNull(pc.params());
    }
}
