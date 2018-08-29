/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

/**
 * Command to backup all activities and bookings into zip file
 */
@Parameters(commandDescription = "backup all bookings and activities into zip file")
public class Restore extends AbstractCommandHandler {
	@Parameter(names = { "-z",
			"--zipfile" }, description = "Name of zipfile to store the data", required = true, converter = FileConverter.class)
	private File zipfile;

	@Override
	public void handleCommand() {
		getLogger().log("Upload Backup ...");
		checkState(zipfile.exists());
		getServices().getInfrastructureServices().restore(zipfile);
		getLogger().log("... done");
	}
}
