/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;

import java.time.LocalTime;
import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * Start a booking now
 */
public class StartBooking extends AbstractCommandHandler {
    @Override
    public void handleCommand(final Collection<String> parameters) {
	DayBookings currentBookings = getServices().getStateStore().getCurrentDay();
	checkState(parameters.size() > 0);
	getLogger().log("Add booking now ...");
	String activityAbbrev = parameters.iterator().next();
	Activity activity = getServices().getActivityService().getActivityByAbbreviatedName(activityAbbrev);
	getLogger().log("For activity: " + activity.getActivityName());
	LocalTime starttime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());
	Booking booking = getServices().getBookingService().addBooking(currentBookings, activity, starttime);
	getLogger().log("... added booking " + booking.toString());
    }

    @Override
    public String toString() {
	return "Start a booking now, Params: <1> Activity";
    }
}
