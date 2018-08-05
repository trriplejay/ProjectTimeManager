/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli;

import static java.lang.System.exit;

/**
 * The main class for the command line interface
 */
public class ProjectTimeManager {
	public static void main(final String ... args) {
		new PTMCLIConfigurator().configure().runCommand(args);
		exit(0);
	}
}
