/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * List the bookings of the day
 */
public class ListBookings extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		DayBookings currentBookings = getServices().getStateStore().getCurrentDay();
		getLogger().log("Current Bookings for day " + currentBookings.getDay().format(ISO_LOCAL_DATE));
		getLogger().log("======================================");
		for (Booking booking : currentBookings.getBookings()) {
			getLogger().log(booking.toString());
		}
		getLogger().log("======================================\n");
	}

	@Override
	public String toString() {
		return "List the bookings of the current day";
	}
}
