package command;

import java.util.List;

public final record CommandDefinition(String command, boolean hasSubject, List<ParamDefinition> params) {
}