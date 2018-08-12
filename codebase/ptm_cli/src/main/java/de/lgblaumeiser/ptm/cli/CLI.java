/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli;

import com.beust.jcommander.JCommander;
import com.google.common.collect.Iterables;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

/**
 * The command line interface, which includes the main loop
 */
public class CLI {
	private JCommander interpreter;

	public void runCommand(final String ... args) {
		interpreter.parse(args);
        if (isHelpTextedRequested()) {
            interpreter.usage();
        } else {
            getParsedCommand().handleCommand();
        }
	}

    private AbstractCommandHandler getParsedCommand() {
        return (AbstractCommandHandler)(Iterables.get(getParsedCommandWrapper().getObjects(), 0));
    }

    private JCommander getParsedCommandWrapper() {
        return interpreter.getCommands().get(interpreter.getParsedCommand());
    }

    private boolean isHelpTextedRequested() {
        return ((MainParameters) Iterables.get(interpreter.getObjects(), 0)).helpNeeded();
    }

    void setInterpreter(final JCommander interpreter) {
		this.interpreter = interpreter;
	}
}
