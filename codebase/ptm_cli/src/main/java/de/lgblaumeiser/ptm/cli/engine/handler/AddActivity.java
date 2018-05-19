/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.get;
import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;

/**
 * Command to add an activity
 */
public class AddActivity extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		checkState(parameters.size() > 1);
		String name = get(parameters, 0);
		String id = get(parameters, 1);
		checkState(isNotBlank(name));
		checkState(isNotBlank(id));
		getLogger().log("Add activity " + name + " with id " + id);
		Activity newAct = getServices().getActivityStore().store(newActivity().setActivityName(name).setBookingNumber(id).build());
		getLogger().log("Activity added with id " + newAct.getId() + "\n");
	}

	@Override
	public String toString() {
		return "Adds an activity to the list of available activities, Params: <1> Activity Name, <2> Activity Id";
	}
}
