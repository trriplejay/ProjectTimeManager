/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine;

import java.util.Collection;

/**
 * Basic interface for running a command. For each command a hander is created.
 */
public interface CommandHandler {
	void handleCommand(Collection<String> parameters);
}
