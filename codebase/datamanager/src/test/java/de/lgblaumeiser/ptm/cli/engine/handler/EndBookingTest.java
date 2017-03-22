/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class EndBookingTest extends AbstractHandlerTest {
	private EndBooking testee = new EndBooking();

	@Override
	@Before
	public void before() {
		super.before();
		services.getBookingService().addBooking(services.getStateStore().getCurrentDay(), ACTIVITY1, TIME1);
	}

	@Test
	public void testEndBookingClean() {
		assertFalse(services.getStateStore().getCurrentDay().getBookings().get(0).hasEndtime());
		testee.handleCommand(asList(TIME2.toString()));
		assertEquals(1, services.getStateStore().getCurrentDay().getBookings().size());
		assertTrue(services.getStateStore().getCurrentDay().getBookings().get(0).hasEndtime());
	}

	@Test
	public void testEndBookingAlreadyEnded() {
		assertFalse(services.getStateStore().getCurrentDay().getBookings().get(0).hasEndtime());
		testee.handleCommand(asList(TIME2.toString()));
		assertEquals(1, services.getStateStore().getCurrentDay().getBookings().size());
		assertEquals(TIME2, services.getStateStore().getCurrentDay().getBookings().get(0).getEndtime());
		testee.handleCommand(asList(TIME2.plusMinutes(10).toString()));
		assertEquals(1, services.getStateStore().getCurrentDay().getBookings().size());
		assertTrue(services.getStateStore().getCurrentDay().getBookings().get(0).hasEndtime());
		assertNotEquals(TIME2, services.getStateStore().getCurrentDay().getBookings().get(0).getEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public void testEndBookingEarlierTime() {
		testee.handleCommand(asList(TIME1.minusMinutes(10).toString()));
	}

	@Test(expected = IllegalStateException.class)
	public void testEndBookingNoParam() {
		testee.handleCommand(emptyList());
	}

	@Test(expected = DateTimeParseException.class)
	public void testEndBookingEmptyParam() {
		testee.handleCommand(asList(StringUtils.EMPTY));
	}

	@Test(expected = DateTimeParseException.class)
	public void testEndBookingWrongTime() {
		testee.handleCommand(asList(ACTIVITY1NUMBER));
	}
}
