/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.CommandHandler;
import de.lgblaumeiser.ptm.cli.engine.CommandLogger;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.service.ActivityService;

/**
 * Command to list all the activities
 *
 */
public class ListActivity implements CommandHandler {
    private ActivityService service;
    private CommandLogger logger;

    public void setService(final ActivityService service) {
	this.service = service;
    }

    @Override
    public void setLogger(final CommandLogger logger) {
	this.logger = logger;
    }

    @Override
    public void handleCommand(final Collection<String> parameters) {
	logger.log("Known Activities");
	logger.log("======================================");
	for (Activity current : service.getActivityModel().getActivities()) {
	    logger.log(current.toString());
	}
	logger.log("======================================\n");
    }

    @Override
    public String toString() {
	return "Lists all activities known";
    }
}
