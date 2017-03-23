/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.get;
import static java.time.LocalTime.parse;
import static java.util.stream.Collectors.toList;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * Add a booking for the day
 */
public class AddBooking extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		DayBookings currentBookings = getServices().getStateStore().getCurrentDay();
		checkState(parameters.size() > 1);
		getLogger().log("Add new booking ...");
		String activityAbbrev = get(parameters, 0);
		Activity activity = getActivityByAbbreviatedName(activityAbbrev);
		LocalTime starttime = parse(get(parameters, 1));
		Booking addedBooking = getServices().getBookingService().addBooking(currentBookings, activity, starttime);
		if (parameters.size() == 3) {
			LocalTime endtime = parse(get(parameters, 2));
			addedBooking = getServices().getBookingService().endBooking(currentBookings, addedBooking, endtime);
		}
		getLogger().log(" ... booking added with information: " + addedBooking.toString());
		getServices().getBookingsStore().store(currentBookings);
		getLogger().log("... bookings stored");
	}

	private Activity getActivityByAbbreviatedName(final String name) {
		checkState(name != null);
		List<Activity> results = getServices().getActivityStore().retrieveAll().stream()
				.filter((activity) -> activity.getActivityName().toUpperCase().startsWith(name.toUpperCase()))
				.collect(toList());
		checkState(results.size() == 1);
		return results.get(0);
	}

	@Override
	public String toString() {
		return "Add a booking for the day, Params: <1> Activity, <2> Starttime, <3o> Endtime";
	}
}