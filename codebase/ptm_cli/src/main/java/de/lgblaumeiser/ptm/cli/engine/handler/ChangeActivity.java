/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;

/**
 * Command to add an activity
 */
@Parameters(commandDescription = "Change an existing activity for bookings")
public class ChangeActivity extends AbstractCommandHandler {
	@Parameter(names = { "-a", "--activity" }, description = "Id of activity to change", required = true)
	private Long id;

	@Parameter(names = { "-n", "--name" }, description = "New name of the activity")
	private String name;

	@Parameter(names = { "-i", "--identifier" }, description = "Change the project identifier of the activity")
	private String identifier;

	@Parameter(names = { "--hidden" }, description = "Hide the activity")
	private boolean hidden = false;

	@Parameter(names = { "--visible" }, description = "Make activity visible, of hidden")
	private boolean visible = false;

	@Override
	public void handleCommand() {
		getLogger().log("Change activity with id " + id);
		Activity oldAct = getActivityById(id);
		Activity.ActivityBuilder chgAct = oldAct.changeActivity();
		if (name != null)
			chgAct.setActivityName(name);
		if (identifier != null)
			chgAct.setBookingNumber(identifier);
		if (oldAct.isHidden() && visible)
			chgAct.setHidden(false);
		if (!oldAct.isHidden() && hidden)
			chgAct.setHidden(true);
		Activity newAct = getServices().getActivityStore().store(chgAct.build());
		getLogger().log("Activity changed " + newAct.toString() + "\n");
	}

	private Activity getActivityById(final Long id) {
		return getServices().getActivityStore().retrieveById(id).orElseThrow(IllegalStateException::new);
	}
}
