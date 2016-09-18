/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * Save the current day bookings to the object store
 */
public class SaveDay extends AbstractCommandHandler {
    @Override
    public void handleCommand(final Collection<String> parameters) {
	DayBookings currentBookings = getServices().getStateStore().getCurrentDay();
	getLogger().log("Storing bookings for day: " + currentBookings.getDay().format(DateTimeFormatter.ISO_LOCAL_DATE)
		+ " ...");
	getServices().getBookingsStore().store(currentBookings);
	getLogger().log("... bookings stored");
    }

    @Override
    public String toString() {
	return "Save the bookings of the current day";
    }
}
