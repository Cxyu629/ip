package parser;

import java.util.Map;

public final record ParsedCommand(
        String commandName,
        String subject,
        Map<String, String> params) {
}