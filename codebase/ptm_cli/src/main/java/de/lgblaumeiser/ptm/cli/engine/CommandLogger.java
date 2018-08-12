/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine;

/**
 * Interface for Output of commands
 */
public interface CommandLogger {
	void log(String message);
}
