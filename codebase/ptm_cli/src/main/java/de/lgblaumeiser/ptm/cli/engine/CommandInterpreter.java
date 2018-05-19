/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Collections.unmodifiableMap;
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
		String commandId = command.toUpperCase();
		checkState(!commandToHandlerMap.containsKey(commandId));
		commandToHandlerMap.put(commandId, handler);
	}

	public Map<String, CommandHandler> listHandler() {
		return unmodifiableMap(commandToHandlerMap);
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
		String commandToken = tokens.nextToken().toUpperCase();
		checkState(isNotBlank(commandToken));
		checkState(isAlpha(commandToken));
		return commandToken;
	}

	private StringTokenizer createTokens(final String command) {
		return new StringTokenizer(command);
	}
}
