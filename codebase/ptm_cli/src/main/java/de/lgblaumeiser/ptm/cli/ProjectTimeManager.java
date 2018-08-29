/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli;

import static java.lang.System.exit;

/**
 * The main class for the command line interface
 */
public class ProjectTimeManager {
	public static void main(final String... args) {
		new PTMCLIConfigurator().configure().runCommand(args);
		exit(0);
	}
}
