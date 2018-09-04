/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli;

import static de.lgblaumeiser.ptm.cli.Utils.getFirstFromCollection;

import com.beust.jcommander.JCommander;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

/**
 * The command line interface, which includes the main loop
 */
public class CLI {
	private JCommander interpreter;

	public void runCommand(final String... args) {
		interpreter.parse(args);
		if (isHelpTextedRequested()) {
			interpreter.usage();
		} else {
			getParsedCommand().handleCommand();
		}
	}

	private AbstractCommandHandler getParsedCommand() {
		return (AbstractCommandHandler) (getFirstFromCollection(getParsedCommandWrapper().getObjects()));
	}

	private JCommander getParsedCommandWrapper() {
		return interpreter.getCommands().get(interpreter.getParsedCommand());
	}

	private boolean isHelpTextedRequested() {
		return ((MainParameters) getFirstFromCollection(interpreter.getObjects())).helpNeeded();
	}

	void setInterpreter(final JCommander interpreter) {
		this.interpreter = interpreter;
	}
}
