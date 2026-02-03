package xyxx.parser;

import java.util.Map;

/**
 * Represents a parsed command with its name, subject, and parameters.
 * 
 * <p>
 * This record encapsulates the structure of a command after it has been
 * parsed from user input or an external source.
 * 
 * @param commandName the name of the command to be executed
 * @param subject     the target subject or entity that the command operates on
 * @param params      a map of parameter names to their corresponding string
 *                    values
 */
public final record ParsedCommand(
                String commandName,
                String subject,
                Map<String, String> params) {
}