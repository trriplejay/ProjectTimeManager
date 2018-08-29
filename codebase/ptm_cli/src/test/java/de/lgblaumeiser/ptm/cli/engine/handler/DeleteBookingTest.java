/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.beust.jcommander.ParameterException;

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
