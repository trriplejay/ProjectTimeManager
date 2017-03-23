/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.collect.Iterables.get;
import static java.time.LocalDate.now;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * Open a day to edit
 */
public class OpenDay extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		LocalDate date = now();
		if (parameters.size() > 0) {
			date = parse(get(parameters, 0));
		}
		getLogger().log("Opening day: " + date.format(ISO_LOCAL_DATE) + " ...");
		DayBookings dayBookings = extractBookings(date);
		getServices().getStateStore().setCurrentDay(dayBookings);
		getLogger().log("... Day opened");
	}

	private DayBookings extractBookings(LocalDate date) {
		Optional<DayBookings> stored = getServices().getBookingsStore().retrieveAll().stream()
				.filter(d -> date.equals(d.getDay())).findFirst();
		return stored.orElse(DayBookings.newDay(date));
	}

	@Override
	public String toString() {
		return "Opens a day for managing bookings. Creates it anew if it does not exist, Params: <1o> Day";
	}
}
