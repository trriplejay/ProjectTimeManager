/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.Iterator;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

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
		boolean project = true;
		if (iter.hasNext()) {
			project = !iter.next().toUpperCase().equals("LINE");
		}
		if (project) {
			getLogger().log("Add activity " + name + " with id " + id + " as project activity");
			getServices().getActivityService().addProjectActivity(name, id);
		} else {
			getLogger().log("Add activity " + name + " with id " + id + " as line activity");
			getServices().getActivityService().addLineActivity(name, id);
		}
		getServices().getActivityStore().store(getServices().getActivityService().getActivityModel());
		getLogger().log("Activity added\n");
	}

	@Override
	public String toString() {
		return "Adds an activity to the list of available activities, Params: <1> Activity Name, <2> Activity Id";
	}
}
