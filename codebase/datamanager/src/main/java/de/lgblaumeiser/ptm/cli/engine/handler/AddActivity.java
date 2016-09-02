/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jdt.annotation.NonNull;

import de.lgblaumeiser.ptm.cli.engine.CommandHandler;
import de.lgblaumeiser.ptm.cli.engine.CommandLogger;
import de.lgblaumeiser.ptm.datamanager.model.ActivityModel;
import de.lgblaumeiser.ptm.datamanager.service.ActivityService;
import de.lgblaumeiser.store.ObjectStore;

/**
 * Command to add an activity
 */
public class AddActivity implements CommandHandler {
    private ActivityService service;
    private ObjectStore<ActivityModel> store;
    private CommandLogger logger;

    public void setService(final ActivityService service) {
	this.service = service;
    }

    public void setStore(final ObjectStore<ActivityModel> store) {
	this.store = store;
    }

    @Override
    public void setLogger(final CommandLogger logger) {
	this.logger = logger;
    }

    @Override
    public void handleCommand(final Collection<String> parameters) {
	checkState(parameters.size() > 1);
	Iterator<String> iter = parameters.iterator();
	@SuppressWarnings("null")
	@NonNull
	String name = iter.next();
	@SuppressWarnings("null")
	@NonNull
	String id = iter.next();
	checkState(isNotBlank(name));
	checkState(isNotBlank(id));
	boolean project = true;
	if (iter.hasNext()) {
	    project = !iter.next().toUpperCase().equals("LINE");
	}
	if (project) {
	    logger.log("Add activity " + name + " with id " + id + " as project activity");
	    service.addProjectActivity(name, id);
	} else {
	    logger.log("Add activity " + name + " with id " + id + " as line activity");
	    service.addLineActivity(name, id);
	}
	store.store(service.getActivityModel());
	logger.log("Activity added\n");
    }

    @Override
    public String toString() {
	return "Adds an activity to the list of available activities";
    }
}
