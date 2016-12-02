/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli;

/**
 * The main class for the command line interface
 */
public class ProjectTimeManager {
	public static void main(final String[] args) {
		PTMCLIConfigurator configurator = new PTMCLIConfigurator();
		CLI cli = configurator.configure();
		cli.runApplication();
		System.exit(0);
	}
}
