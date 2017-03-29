/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.get;
import static java.lang.Long.valueOf;

import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

/**
 * Delete a booking
 */
public class DeleteBooking extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		checkState(parameters.size() > 0);
		getLogger().log("Delete booking now ...");
		Long id = valueOf(get(parameters, 0));
		getServices().getBookingsStore().deleteById(id);
		getLogger().log("... booking deleted");
	}

	@Override
	public String toString() {
		return "Delete a booking, Params: <1> Starttime of booking";
	}

}
