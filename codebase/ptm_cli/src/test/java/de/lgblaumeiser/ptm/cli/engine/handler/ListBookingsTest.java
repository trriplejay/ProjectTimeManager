/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ListBookingsTest extends AbstractHandlerTest {
	private ListBookings testee = new ListBookings();

	@Test
	public void test() {
		services.getBookingService().addBooking(services.getStateStore().getCurrentDay(), ACTIVITY1, TIME1);
		testee.handleCommand(emptyList());
		assertTrue(logger.logMessages.toString().contains(ACTIVITY1NAME));
		assertTrue(logger.logMessages.toString().contains(ACTIVITY1NUMBER));
		assertTrue(logger.logMessages.toString().contains(TIME1.toString()));
	}
}
