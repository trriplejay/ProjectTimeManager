/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
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
