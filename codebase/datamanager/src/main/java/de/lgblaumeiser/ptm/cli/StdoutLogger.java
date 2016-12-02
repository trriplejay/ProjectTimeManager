/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli;

import de.lgblaumeiser.ptm.cli.engine.CommandLogger;

/**
 * Logs the output to stdio
 */
public class StdoutLogger implements CommandLogger {
	@Override
	public void log(final String message) {
		System.out.println(message);
	}
}
