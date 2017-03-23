/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class DeleteBookingTest extends AbstractHandlerTest {
	private DeleteBooking testee = new DeleteBooking();

	@Override
	@Before
	public void before() {
		super.before();
		services.getStateStore().getCurrentDay().addBooking(BOOKING1);
	}

	@Test
	public void testDeleteBookingClean() {
		testee.handleCommand(asList(TIME1.toString()));
		assertEquals(0, services.getStateStore().getCurrentDay().getBookings().size());
	}

	@Test
	public void testDeleteBookingUnknownBooking() {
		testee.handleCommand(asList(TIME2.toString()));
		assertEquals(1, services.getStateStore().getCurrentDay().getBookings().size());
	}

	@Test(expected = IllegalStateException.class)
	public void testDeleteBookingNoParam() {
		testee.handleCommand(emptyList());
	}

	@Test(expected = DateTimeParseException.class)
	public void testDeleteBookingEmptyParam() {
		testee.handleCommand(asList(StringUtils.EMPTY));
	}

	@Test(expected = DateTimeParseException.class)
	public void testDeleteBookingWrongTime() {
		testee.handleCommand(asList(ACTIVITY1NUMBER));
	}
}
