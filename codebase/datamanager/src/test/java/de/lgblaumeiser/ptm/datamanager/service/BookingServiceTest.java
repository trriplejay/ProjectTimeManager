/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * Tests for the DayBookings service
 */
public class BookingServiceTest {
	private static final LocalDate DATE1 = LocalDate.of(2016, 06, 24);
	private static final LocalTime TIME1 = LocalTime.of(10, 12);
	private static final LocalTime TIME2 = LocalTime.of(12, 15);
	private static final LocalTime TIME3 = LocalTime.of(13, 45);
	private static final Activity ACTIVITY1 = Activity.newLineActivity("a", "b");
	private static final Activity ACTIVITY2 = Activity.newLineActivity("a1", "c");
	private static final Booking BOOKING1 = Booking.newBooking().setStarttime(TIME1).setActivity(ACTIVITY1).build();

	private final DayBookings testBookings = DayBookings.newDay(DATE1);
	private final BookingService testee = new BookingServiceImpl();

	@Test(expected = IllegalStateException.class)
	public void testAddBooking0Empty() {
		testee.addBooking(testBookings, ACTIVITY1);
	}

	@Test
	public void testAddBooking0Positive() {
		Booking firstOne = testee.addBooking(testBookings, ACTIVITY1, TIME1);
		testee.endBooking(testBookings, firstOne, TIME2);
		Booking testBooking = testee.addBooking(testBookings, ACTIVITY2);
		assertFalse(testBookings.getBookings().isEmpty());
		assertEquals(2, testBookings.getBookings().size());
		assertEquals(testBooking, testBookings.getLastBooking());
		assertEquals(TIME2, testBookings.getLastBooking().getStarttime());
		assertFalse(testBookings.getLastBooking().hasEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBooking0NoEndtime() {
		testee.addBooking(testBookings, ACTIVITY1, TIME1);
		testee.addBooking(testBookings, ACTIVITY2);
	}

	@Test
	public void testAddBooking1Empty() {
		Booking booking = testee.addBooking(testBookings, ACTIVITY1, TIME1);
		assertFalse(testBookings.getBookings().isEmpty());
		assertEquals(1, testBookings.getBookings().size());
		assertEquals(booking, testBookings.getLastBooking());
		assertFalse(testBookings.getLastBooking().hasEndtime());
	}

	@Test
	public void testAddBooking1With2Bookings() {
		testee.addBooking(testBookings, ACTIVITY1, TIME1);
		Booking testBooking = testee.addBooking(testBookings, ACTIVITY2, TIME2);
		assertEquals(2, testBookings.getBookings().size());
		assertEquals(testBooking, testBookings.getLastBooking());
		assertFalse(testBookings.getLastBooking().hasEndtime());
		assertTrue(testBookings.getBookings().get(0).hasEndtime());
	}

	@Test
	public void testAddBooking1WithEndedLastBooking() {
		Booking firstOne = testee.addBooking(testBookings, ACTIVITY1, TIME1);
		testee.endBooking(testBookings, firstOne, TIME2);
		Booking secondOne = testee.addBooking(testBookings, ACTIVITY2, TIME3);
		assertEquals(2, testBookings.getBookings().size());
		assertEquals(secondOne, testBookings.getLastBooking());
		assertFalse(testBookings.getLastBooking().hasEndtime());
		assertTrue(testBookings.getBookings().get(0).hasEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBooking1WithSameStarttime() {
		testee.addBooking(testBookings, ACTIVITY1, TIME1);
		testee.addBooking(testBookings, ACTIVITY2, TIME1);
	}

	@Test
	public void testEndBooking() {
		Booking booking = testee.addBooking(testBookings, ACTIVITY1, TIME1);
		Booking result = testee.endBooking(testBookings, booking, TIME2);
		assertFalse(testBookings.getBookings().isEmpty());
		assertEquals(1, testBookings.getBookings().size());
		assertEquals(result, testBookings.getLastBooking());
		assertTrue(testBookings.getLastBooking().hasEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public void testEndBookingSecondEnd() {
		Booking booking = testee.addBooking(testBookings, ACTIVITY1, TIME1);
		Booking endedBooking = testee.endBooking(testBookings, booking, TIME2);
		testee.endBooking(testBookings, endedBooking, TIME3);
	}

	@Test(expected = IllegalStateException.class)
	public void testEndBookingWrongBookingTime() {
		try {
			Booking testBooking = testee.addBooking(testBookings, ACTIVITY1, TIME2);
			testee.endBooking(testBookings, testBooking, TIME1);
		} finally {
			assertFalse(testBookings.getBookings().isEmpty());
		}
	}

	@Test(expected = IllegalStateException.class)
	public void testEndBookingWithAnUnknownBooking() {
		testee.endBooking(testBookings, BOOKING1, TIME3);
	}

	@Test
	public void testRemoveBooking() {
		Booking testBooking = testee.addBooking(testBookings, ACTIVITY1, TIME1);
		testee.removeBooking(testBookings, testBooking);
		assertTrue(testBookings.getBookings().isEmpty());
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveBookingUnknownBooking() {
		testee.removeBooking(testBookings, BOOKING1);
	}

	@Test
	public void testRemoveBookingWithPreviousBooking() {
		testee.addBooking(testBookings, ACTIVITY1, TIME1);
		Booking startBooking = testee.addBooking(testBookings, ACTIVITY2, TIME2);
		Booking testBooking = testee.endBooking(testBookings, startBooking, TIME3);
		testee.removeBooking(testBookings, testBooking);
		assertEquals(1, testBookings.getBookings().size());
		assertEquals(TIME3, testBookings.getLastBooking().getEndtime());
		assertEquals(TIME1, testBookings.getLastBooking().getStarttime());
	}

	@Test
	public void testRemoveBookingWithPreviousBookingButUnendedRemoveCandidate() {
		testee.addBooking(testBookings, ACTIVITY1, TIME1);
		Booking testBooking = testee.addBooking(testBookings, ACTIVITY2, TIME2);
		testee.removeBooking(testBookings, testBooking);
		assertEquals(1, testBookings.getBookings().size());
		assertEquals(TIME1, testBookings.getLastBooking().getStarttime());
		assertFalse(testBookings.getLastBooking().hasEndtime());
	}

	@Test
	public void testRemoveBookingFirst() {
		testee.addBooking(testBookings, ACTIVITY1, TIME1);
		testee.addBooking(testBookings, ACTIVITY2, TIME2);
		testee.removeBooking(testBookings, testBookings.getBookings().get(0));
		assertEquals(1, testBookings.getBookings().size());
		assertEquals(TIME2, testBookings.getLastBooking().getStarttime());
	}
}
