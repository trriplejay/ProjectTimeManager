/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.ParameterException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeleteBookingTest extends AbstractHandlerTest {
    private static final String DELETE_BOOKING_COMMAND = "delete_booking";

	@Test
	public void testDeleteBookingClean() {
        commandline.runCommand(DELETE_BOOKING_COMMAND, "-b", "1");
        assertEquals("/bookings/id/1", restutils.apiNameGiven);
	}

	@Test(expected = ParameterException.class)
	public void testDeleteBookingNoParam() {
        commandline.runCommand(DELETE_BOOKING_COMMAND);
	}

	@Test(expected = ParameterException.class)
	public void testDeleteBookingEmptyParam() {
        commandline.runCommand(DELETE_BOOKING_COMMAND, "-b");
	}
}
