/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import java.util.Collection;

import com.beust.jcommander.Parameters;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;

/**
 * Command to list all the activities
 */
@Parameters(commandDescription = "List all activities available")
public class ListActivity extends AbstractCommandHandler {
	@Override
	public void handleCommand() {
		Collection<Activity> result = getServices().getActivityStore().retrieveAll();
		getPrinter().activityPrint(result);
	}
}
