/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * List the bookings of the day
 */
public class ListBookings extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		String currentBookings = getServices().getStateStore().getCurrentDayString();
		getLogger().log("Current Bookings for day " + currentBookings);
		getLogger().log("======================================");
		for (Booking booking : getServices().getRestUtils().getBookingsForDay(currentBookings)) {
			getLogger().log(booking.toString());
		}
		getLogger().log("======================================\n");
	}

	@Override
	public String toString() {
		return "List the bookings of the current day";
	}
}
