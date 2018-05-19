/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
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
