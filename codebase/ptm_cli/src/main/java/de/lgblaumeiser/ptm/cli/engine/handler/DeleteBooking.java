/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

/**
 * Delete a booking
 */
@Parameters(commandDescription="Delete an existing booking")
public class DeleteBooking extends AbstractCommandHandler {
	@Parameter(names = { "-b", "--booking" }, description="Booking id of the booking to end", required=true)
	private Long id;

	@Override
	public void handleCommand() {
		getLogger().log("Delete booking now ...");
		getServices().getBookingsStore().deleteById(id);
		getLogger().log("... booking deleted");
	}
}
