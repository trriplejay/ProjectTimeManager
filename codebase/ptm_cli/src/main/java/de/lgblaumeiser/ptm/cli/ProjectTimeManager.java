/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli;

import static java.lang.System.exit;

/**
 * The main class for the command line interface
 */
public class ProjectTimeManager {
	public static void main(final String[] args) {
		new PTMCLIConfigurator().configure().runApplication();
		exit(0);
	}
}
