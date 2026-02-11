package xyxx.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyxx.command.CommandDefinition;
import xyxx.command.ParamDefinition;
import xyxx.datetime.PartialDateTime;

/**
 * Parses CLI-like commands into a {@link ParsedCommand} using a registry of
 * {@link CommandDefinition} objects.
 */
public final class Parser {

    private final record ChunkToken(String chunk, int position) {
    }

    private final Map<String, CommandDefinition> registry;

    /**
     * Creates a Parser backed by the provided command registry.
     *
     * @param registry mapping of command names to definitions
     */
    public Parser(Map<String, CommandDefinition> registry) {
        this.registry = registry;
        assert this.registry != null : "Registry must not be null.";
    }

    /**
     * Parses the raw command line input into a {@link ParsedCommand}.
     *
     * @param input the raw command line input
     * @return a parsed command; if the command is unknown the returned
     *         {@link ParsedCommand} will have an empty {@code commandName} and
     *         {@code null} {@code params}
     * @throws ParseException on malformed input, missing required parameters,
     *                        unexpected parameters, or invalid parameter values
     */
    public ParsedCommand parse(String input) throws ParseException {
        List<ChunkToken> chunks = tokenize(input);

        String[] head = chunks.get(0).chunk().split("\\s+", 2);
        String commandName = head[0].toLowerCase();
        String subject = head.length > 1 ? head[1] : "";

        CommandDefinition definition = registry.get(commandName);
        if (definition == null) {
            return new ParsedCommand("", input, null);
        }

        Map<String, String> params = parseParams(definition, chunks.subList(1, chunks.size()));

        return new ParsedCommand(commandName, subject, params);
    }

    private List<ChunkToken> tokenize(String input) {
        List<ChunkToken> chunks = new ArrayList<>();
        int position = 0;
        for (String chunk : input.split("(?=\\/\\w)")) {
            String trimmed = chunk.trim();
            if (!trimmed.isBlank()) {
                chunks.add(new ChunkToken(trimmed, position));
            }
            position += chunk.length();
        }
        return chunks;
    }

    private Map<String, String> parseParams(CommandDefinition definition,
            List<ChunkToken> paramChunks) throws ParseException {
        Map<String, String> params = new HashMap<>();
        Pattern paramPattern = Pattern.compile("^\\/(\\w+)(\\s+(.*))?$");

        for (ChunkToken chunkToken : paramChunks) {
            String chunk = chunkToken.chunk();
            Matcher matcher = paramPattern.matcher(chunk);
            if (matcher.matches()) {
                String key = matcher.group(1);
                String value = matcher.group(3) != null ? matcher.group(3).trim() : "";
                Optional<ParamDefinition> paramDef = findParamDef(definition, key);
                if (paramDef.isEmpty()) {
                    throw new ParseException("Unexpected parameter: " + key, chunkToken.position());
                }
                validateParam(paramDef.get(), value, chunkToken.position());
                params.put(key, value);
            } else {
                throw new ParseException("Invalid parameter format: " + chunk,
                        chunkToken.position());
            }
        }

        for (ParamDefinition param : definition.params()) {
            if (param.isRequired() && !params.containsKey(param.name())) {
                throw new ParseException("Missing required parameter: " + param.name(), 0);
            }
        }

        return params;
    }

    private Optional<ParamDefinition> findParamDef(CommandDefinition definition, String paramName) {
        return definition.params().stream().filter(p -> p.name().equals(paramName)).findFirst();
    }

    private void validateParam(ParamDefinition paramDef, String value, int position)
            throws ParseException {
        if (paramDef.isRequired() && value.isBlank()) {
            throw new ParseException("Parameter " + paramDef.name() + " is required.", position);
        }
        if (value.isBlank()) {
            return;
        }
        switch (paramDef.type()) {
        case STRING:
            break;
        case NUMBER:
            try {
                Double.parseDouble(value);
            } catch (NumberFormatException e) {
                throw new ParseException("Invalid value for number parameter: " + paramDef.name(),
                        position);
            }
            break;
        case PARTIALDATETIME:
            if (PartialDateTime.fromString(value) == null) {
                throw new ParseException("Invalid value for datetime parameter: " + paramDef.name(),
                        position);
            }
            break;
        default:
            assert false : "Unhandled ParamType: " + paramDef.type();
            throw new UnsupportedOperationException("Unsupported parameter type: " + paramDef.type());
        }
    }
}
