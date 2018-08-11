/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.stream.Collectors.toList;

/**
 * List the bookings of the day
 */
@Parameters(commandDescription="List all bookings of a day")
public class ListBookings extends AbstractCommandHandler {
	@Parameter(names = { "-d", "--day" }, description="Optional day for bookings", converter= LocalDateConverter.class)
	private LocalDate bookingDay = LocalDate.now();

	@Override
	public void handleCommand() {
		getLogger().log("Current Bookings for day " + bookingDay.format(ISO_LOCAL_DATE));
		getLogger().log(" ");
		Collection<Booking> result = getServices().getBookingsStore().retrieveForDay(bookingDay).stream()
				.sorted(Comparator.comparing(Booking::getStarttime)).collect(toList());
		getPrinter().bookingPrint(result);
	}
}
