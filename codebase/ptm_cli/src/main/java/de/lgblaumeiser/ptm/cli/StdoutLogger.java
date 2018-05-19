/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli;

import static java.lang.System.out;

import de.lgblaumeiser.ptm.cli.engine.CommandLogger;

/**
 * Logs the output to stdio
 */
public class StdoutLogger implements CommandLogger {
	@Override
	public void log(final String message) {
		out.println(message);
	}
}
