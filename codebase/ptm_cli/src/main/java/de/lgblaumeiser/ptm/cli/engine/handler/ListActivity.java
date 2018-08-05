/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameters;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;

/**
 * Command to list all the activities
 *
 */
@Parameters(commandDescription="List all activities available")
public class ListActivity extends AbstractCommandHandler {
	@Override
	public void handleCommand() {
		getLogger().log("Known Activities");
		getLogger().log("======================================");
		for (Activity current : getServices().getActivityStore().retrieveAll()) {
			getLogger().log(current.toString());
		}
		getLogger().log("======================================\n");
	}
}
