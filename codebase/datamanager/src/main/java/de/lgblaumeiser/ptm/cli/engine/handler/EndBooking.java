/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.LocalTime;
import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * End a booking that has been started with start booking command
 */
public class EndBooking extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		DayBookings currentBookings = getServices().getStateStore().getCurrentDay();
		getLogger().log("End booking ...");
		checkState(parameters.size() > 0);
		String timestring = parameters.iterator().next();
		checkState(isNotBlank(timestring));
		LocalTime endtime = LocalTime.parse(timestring);
		Booking booking = getServices().getBookingService().endBooking(currentBookings,
				currentBookings.getLastBooking(), endtime);
		getLogger().log("... new booking data: " + booking.toString());
		getServices().getBookingsStore().store(currentBookings);
		getLogger().log("... bookings stored");
	}

	@Override
	public String toString() {
		return "End an open booking now";
	}
}
