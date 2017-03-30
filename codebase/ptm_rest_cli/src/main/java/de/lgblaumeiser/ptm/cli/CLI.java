/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli;

import static java.lang.System.err;
import static java.lang.System.out;

import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

import de.lgblaumeiser.ptm.cli.engine.CommandHandler;
import de.lgblaumeiser.ptm.cli.engine.CommandInterpreter;

/**
 * The command line interface, which includes the main loop
 */
public class CLI {
	private CommandInterpreter interpreter;

	public void runApplication() {
		boolean runnit = true;
		try (Scanner terminalIn = new Scanner(System.in)) {
			while (runnit) {
				out.println("Command: ");
				String command = terminalIn.nextLine();
				if (command.toUpperCase().equals("X")) {
					runnit = false;
				} else if (command.toUpperCase().equals("H")) {
					printCommandHelp();
				} else {
					try {
						interpreter.handle(command);
					} catch (IllegalStateException | NullPointerException | DateTimeParseException e) {
						err.println(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void printCommandHelp() {
		out.println("\nCommands:");
		out.println("=======================================");
		out.println("\tX\tExit program");
		out.println("\tH\tShow this help");
		for (Map.Entry<String, CommandHandler> current : interpreter.listHandler().entrySet()) {
			out.println("\t" + current.getKey() + "\t" + current.getValue().toString());
		}
		out.println("=======================================\n");
	}

	void setInterpreter(final CommandInterpreter interpreter) {
		this.interpreter = interpreter;
	}
}
