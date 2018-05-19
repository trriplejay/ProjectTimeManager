/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;

/**
 * Command to list all the activities
 *
 */
public class ListActivity extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		getLogger().log("Known Activities");
		getLogger().log("======================================");
		for (Activity current : getServices().getActivityStore().retrieveAll()) {
			getLogger().log(current.toString());
		}
		getLogger().log("======================================\n");
	}

	@Override
	public String toString() {
		return "Lists all activities known";
	}
}
