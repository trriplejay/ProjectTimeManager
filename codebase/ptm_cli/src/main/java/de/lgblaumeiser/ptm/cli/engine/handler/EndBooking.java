/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.get;
import static java.lang.Long.valueOf;
import static java.time.LocalTime.parse;

import java.time.LocalTime;
import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

/**
 * End a booking that has been started with start booking command
 */
public class EndBooking extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		getLogger().log("End booking ...");
		checkState(parameters.size() > 1);
		Long id = valueOf(get(parameters, 0));
		LocalTime endtime = parse(get(parameters, 1));
		getServices().getBookingsStore().retrieveById(id).ifPresent(b -> {
			getServices().getBookingService().endBooking(b, endtime);
			getLogger().log("... new booking data: " + b.toString());
		});
	}

	@Override
	public String toString() {
		return "End an open booking now";
	}
}
