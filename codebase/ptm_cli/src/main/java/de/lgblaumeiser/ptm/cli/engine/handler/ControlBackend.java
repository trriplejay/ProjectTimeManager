/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static de.lgblaumeiser.ptm.util.Utils.stringHasContent;
import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

/**
 * Command to add an activity
 */
@Parameters(commandDescription = "Boot the backend, requires an existing docker-compose installation in version 1.22.0 or higher")
public class ControlBackend extends AbstractCommandHandler {
	@Parameter(names = { "--start" }, description = "Start the backend")
	private boolean start;

	@Parameter(names = { "--stop" }, description = "Stop the backend")
	private boolean stop;

	@Parameter(names = { "--status" }, description = "Check status of the backend")
	private boolean status;

	@Override
	public void handleCommand() {
		try {
			getLogger().log("Check status of PTM backend...");
			boolean isRunning = isBackendRunning();

			if (start && !isRunning) {
				getLogger().log("Start PTM backend...");
				startBackendProcess("up", "-d");
				getLogger().log("PTM backend is running, stay tuned until system is initialized...");
			} else if (stop && isRunning) {
				getLogger().log("PTM backend shutting down...");
				startBackendProcess("down");
				getLogger().log("...done");
			} else if (status) {
				getLogger().log(isRunning ? "PTM backend running!" : "PTM backend down!");
			} else {
				getLogger().log("Nothing to do!");
			}
		} catch (IOException | InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	private void startBackendProcess(String... command) throws IOException, InterruptedException {
		List<String> commands = asList("sudo", "docker-compose");
		commands.addAll(asList(command));
		BufferedReader outputReader = startOutputGeneratingProcess(commands);
		String line;
		while ((line = outputReader.readLine()) != null) {
			getLogger().log(line);
		}
	}

	private boolean isBackendRunning() throws IOException, InterruptedException {
		BufferedReader outputReader = startOutputGeneratingProcess(asList("sudo", "docker-compose", "ps"));
		String message = IOUtils.toString(outputReader);
		if (stringHasContent(message)) {
			return message.contains("_ptm");
		}
		return false;
	}

	private BufferedReader startOutputGeneratingProcess(List<String> commands)
			throws IOException, InterruptedException {
		String[] commandarray = commands.toArray(new String[commands.size()]);
		Process process = new ProcessBuilder(commandarray).redirectErrorStream(true)
				.redirectOutput(ProcessBuilder.Redirect.PIPE).start();
		BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		process.waitFor();
		return outputReader;
	}
}
