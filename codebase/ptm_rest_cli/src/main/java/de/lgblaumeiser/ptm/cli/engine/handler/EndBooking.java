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

/**
 * End a booking that has been started with start booking command
 */
public class EndBooking extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		LocalDate currentDay = getServices().getStateStore().getCurrentDay();
		getLogger().log("End booking ...");
		checkState(parameters.size() > 0);
		LocalTime endtime = parse(get(parameters, 0));
		getServices().getBookingsStore().retrieveAll().stream()
				.filter(b -> currentDay.equals(b.getBookingday()) && !b.hasEndtime()).findFirst().ifPresent(b -> {
					getServices().getBookingService().endBooking(b, endtime);
					getLogger().log("... new booking data: " + b.toString());
				});
	}

	@Override
	public String toString() {
		return "End an open booking now";
	}
}
