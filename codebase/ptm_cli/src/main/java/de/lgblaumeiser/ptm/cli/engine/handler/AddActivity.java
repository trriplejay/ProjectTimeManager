/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;

/**
 * Command to add an activity
 */
@Parameters(commandDescription="Define a new activity for bookings")
public class AddActivity extends AbstractCommandHandler {
	@Parameter(names = { "-n", "--name" }, description="Name of the new activity", required=true)
	private String name;

	@Parameter(names = { "-i", "--identifier" }, description="Unique identifier of the activity (e.g. project number)", required=true)
	private String identifier;

	@Override
	public void handleCommand() {
		getLogger().log("Add activity " + name + " with id " + identifier);
		Activity newAct = getServices().getActivityStore().store(newActivity().setActivityName(name).setBookingNumber(identifier).build());
		getLogger().log("Activity added with id " + newAct.getId() + "\n");
	}
}
