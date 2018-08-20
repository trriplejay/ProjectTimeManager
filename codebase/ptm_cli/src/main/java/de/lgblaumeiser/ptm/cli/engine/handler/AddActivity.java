/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;

import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;

/**
 * Command to add an activity
 */
@Parameters(commandDescription="Define a new activity for bookings")
public class AddActivity extends AbstractCommandHandler {
	@Parameter(names = { "-n", "--name" }, description="Name of the new activity", required=true)
	private String name;

	@Parameter(names = { "-i", "--identifier" }, description="Project identifier of the activity", required=true)
	private String identifier;

	@Override
	public void handleCommand() {
		getLogger().log("Add activity " + name + " with id " + identifier);
		Activity newAct = getServices().getActivityStore().store(newActivity().setActivityName(name).setBookingNumber(identifier).build());
		getLogger().log("Activity added: " + newAct.toString() + "\n");
	}
}
