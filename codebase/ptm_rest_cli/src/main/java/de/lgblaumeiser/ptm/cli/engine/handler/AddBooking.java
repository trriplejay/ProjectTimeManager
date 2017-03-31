/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.get;
import static java.time.LocalTime.parse;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

/**
 * Add a booking for the day
 */
public class AddBooking extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		String currentDay = getServices().getStateStore().getCurrentDayString();
		checkState(parameters.size() > 1);
		getLogger().log("Add new booking ...");
		String activityId = get(parameters, 0);
		LocalTime starttime = parse(get(parameters, 1));
		Optional<LocalTime> endtime = Optional.empty();
		if (parameters.size() == 3) {
			endtime = Optional.of(parse(get(parameters, 2)));
		}
		String bookingId = getServices().getRestUtils().postBooking(currentDay, activityId, starttime, endtime);
		getLogger().log(" ... booking added with id: " + bookingId);
	}

	@Override
	public String toString() {
		return "Add a booking for the day, Params: <1> Activity Id, <2> Starttime, <3o> Endtime";
	}
}