/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Collections.unmodifiableCollection;
import static org.apache.commons.lang3.StringUtils.isAlpha;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * The central parsing and distributing component of the command line client
 */
public class CommandInterpreter {
    private final Map<String, CommandHandler> commandToHandlerMap = newHashMap();

    public void addCommandHandler(final String command, final CommandHandler handler) {
	checkState(isNotBlank(command));
	String commandId = command.substring(0, 1).toUpperCase();
	checkState(!commandToHandlerMap.containsKey(commandId));
	commandToHandlerMap.put(commandId, handler);
    }

    public Collection<CommandHandler> listHandler() {
	return unmodifiableCollection(commandToHandlerMap.values());
    }

    public void handle(final String command) {
	StringTokenizer tokens = createTokens(command);
	String commandToken = getCommandToken(tokens);
	CommandHandler handler = getHandler(commandToken);
	Collection<String> parameters = extractParameters(handler, tokens);
	handler.handleCommand(parameters);
    }

    private Collection<String> extractParameters(final CommandHandler handler, final StringTokenizer tokens) {
	Collection<String> back = newArrayList();
	while (tokens.hasMoreTokens()) {
	    back.add(tokens.nextToken());
	}
	return back;
    }

    private CommandHandler getHandler(final String commandToken) {
	CommandHandler handler = commandToHandlerMap.get(commandToken);
	checkState(handler != null);
	return handler;
    }

    private String getCommandToken(final StringTokenizer tokens) {
	checkState(tokens.hasMoreTokens());
	String rawCommandToken = tokens.nextToken();
	checkState(isNotBlank(rawCommandToken));
	String commandChar = rawCommandToken.substring(0, 1).toUpperCase();
	checkState(isAlpha(commandChar));
	return commandChar;
    }

    private StringTokenizer createTokens(final String command) {
	return new StringTokenizer(command);
    }
}
