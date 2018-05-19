/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.collect.Iterables.get;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.format.DateTimeParseException;
import java.util.Collection;

import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Booking;

public class AddBookingTest extends AbstractHandlerTest {
	private AddBooking testee = new AddBooking();

	@Test
	public void testAddBookingThreeParamClean() {
		testee.handleCommand(asList(Long.toString(1L), USER, TIME1.toString()));
		Collection<Booking> results = services.getBookingsStore().retrieveAll();
		assertEquals(1, results.size());
		assertEquals(ACTIVITY1, get(results, 0).getActivity());
		assertEquals(TIME1, get(results, 0).getStarttime());
		assertFalse(get(results, 0).hasEndtime());
	}

	@Test
	public void testAddBookingFourParamClean() {
		testee.handleCommand(asList(Long.toString(1L), USER, TIME1.toString(), TIME2.toString()));
		Collection<Booking> results = services.getBookingsStore().retrieveAll();
		assertEquals(1, results.size());
		assertEquals(ACTIVITY1, get(results, 0).getActivity());
		assertEquals(TIME1, get(results, 0).getStarttime());
		assertEquals(TIME2, get(results, 0).getEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBookingNoParam() {
		testee.handleCommand(emptyList());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBookingOneParam() {
		testee.handleCommand(asList(Long.toString(1L)));
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBookingTwoParam() {
		testee.handleCommand(asList(Long.toString(1L), USER));
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBookingThreeParamWrongActivity() {
		testee.handleCommand(asList(Long.toString(3L), USER, TIME1.toString()));
	}

	@Test(expected = DateTimeParseException.class)
	public void testAddBookingThreeParamEmptyTime() {
		testee.handleCommand(asList(Long.toString(1L), USER, EMPTY));
	}

	@Test(expected = DateTimeParseException.class)
	public void testAddBookingTwoParamWrongTime() {
		testee.handleCommand(asList(Long.toString(1L), USER, ACTIVITY1NUMBER));
	}

	@Test(expected = DateTimeParseException.class)
	public void testAddBookingThreeParamWrongTime() {
		testee.handleCommand(asList(Long.toString(1L), USER, TIME1.toString(), ACTIVITY1NUMBER));
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBookingThreeParamWrongTimeSequence() {
		testee.handleCommand(asList(Long.toString(1L), USER, TIME2.toString(), TIME1.toString()));
	}
}
