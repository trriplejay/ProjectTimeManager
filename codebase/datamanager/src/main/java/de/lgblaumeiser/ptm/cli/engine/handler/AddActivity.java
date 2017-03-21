/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.Iterator;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;

/**
 * Command to add an activity
 */
public class AddActivity extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		checkState(parameters.size() > 1);
		Iterator<String> iter = parameters.iterator();
		String name = iter.next();
		String id = iter.next();
		checkState(isNotBlank(name));
		checkState(isNotBlank(id));
		getLogger().log("Add activity " + name + " with id " + id);
		Activity newAct = getServices().getActivityStore().store(Activity.newActivity(name, id));
		getLogger().log("Activity added with id " + newAct.getId() + "\n");
	}

	@Override
	public String toString() {
		return "Adds an activity to the list of available activities, Params: <1> Activity Name, <2> Activity Id";
	}
}
