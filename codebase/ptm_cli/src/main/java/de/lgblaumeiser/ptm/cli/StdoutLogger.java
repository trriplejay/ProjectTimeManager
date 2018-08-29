/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
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
