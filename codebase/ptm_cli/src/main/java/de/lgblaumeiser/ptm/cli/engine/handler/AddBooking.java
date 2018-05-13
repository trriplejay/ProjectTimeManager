/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.get;
import static java.time.LocalTime.parse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * Add a booking for the day
 */
public class AddBooking extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		LocalDate currentDay = getServices().getStateStore().getCurrentDay();
		checkState(parameters.size() > 2);
		getLogger().log("Add new booking ...");
		Activity activity = getActivityById(get(parameters, 0));
		String user = get(parameters, 1);
		LocalTime starttime = parse(get(parameters, 2));
		Booking addedBooking = getServices().getBookingService().addBooking(currentDay, user, activity, starttime, "");
		if (parameters.size() == 4) {
			LocalTime endtime = parse(get(parameters, 3));
			addedBooking = getServices().getBookingService().endBooking(addedBooking, endtime);
		}
		getLogger().log(" ... booking added with id: " + addedBooking.getId());
	}

	private Activity getActivityById(final String id) {
		checkState(id != null);
		return getServices().getActivityStore().retrieveById(Long.parseLong(id))
				.orElseThrow(IllegalStateException::new);
	}

	@Override
	public String toString() {
		return "Add a booking for the day, Params: <1> Activity, <2> User, <3> Starttime, <4o> Endtime";
	}
}