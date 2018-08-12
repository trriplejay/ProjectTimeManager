/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

import java.time.LocalTime;
import java.util.Optional;

/**
 * End a booking that has been started with start booking command
 */
@Parameters(commandDescription="Add an end time to an existing booking")
public class EndBooking extends AbstractCommandHandler {
	@Parameter(names = { "-b", "--booking" }, description="Booking id of the booking to end", required=true)
	private Long id;

	@Parameter(names = { "-e", "--endtime" }, description="End time of the booking", required=true, converter= LocalTimeConverter.class)
	private Optional<LocalTime> endtime = Optional.empty();

	@Override
	public void handleCommand() {
		getLogger().log("End booking ...");
		getServices().getBookingsStore().retrieveById(id).ifPresent(b -> {
			getServices().getBookingService().endBooking(b, endtime.get());
			getLogger().log("... new booking data: " + b.toString());
		});
	}
}
