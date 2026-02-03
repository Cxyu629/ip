package xyxx.command;

import java.util.List;

/**
 * Definition of a command accepted by the parser.
 *
 * @param command the command name
 * @param hasSubject whether the command expects a subject (free text)
 * @param params parameter definitions for the command
 */
public final record CommandDefinition(String command, boolean hasSubject,
        List<ParamDefinition> params) {
}
