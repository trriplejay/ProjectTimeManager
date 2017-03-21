/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

public class DayBookingsTest {
	private static final LocalDate DATE1 = LocalDate.of(2016, 06, 24);

	private static final LocalTime TIME1 = LocalTime.of(12, 34);

	private static final LocalTime TIME2 = LocalTime.of(13, 57);

	private static final LocalTime TIME3 = LocalTime.of(14, 35);

	private static final Activity ACTIVITY1 = Activity.newActivity("Act1", "0815");

	private static final Booking BOOKING1 = Booking.newBooking().setStarttime(TIME1).setEndtime(TIME2)
			.setActivity(ACTIVITY1).build();

	private static final Booking BOOKING2 = Booking.newBooking().setStarttime(TIME2).setEndtime(TIME3)
			.setActivity(ACTIVITY1).build();

	private static final Booking BOOKING3 = Booking.newBooking().setStarttime(TIME2).setActivity(ACTIVITY1).build();

	private static final Booking BOOKING4 = Booking.newBooking().setStarttime(TIME1).setEndtime(TIME3)
			.setActivity(ACTIVITY1).build();

	private final DayBookings testee = DayBookings.newDay(DATE1);

	@Test
	public void testGetDay() {
		assertEquals(DATE1, testee.getDay());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testBookingListUnmodifiability() {
		testee.getBookings().add(BOOKING1);
	}

	@Test
	public void testAddBooking() {
		testee.addBooking(BOOKING1);
		assertEquals(1, testee.getBookings().size());
		assertEquals(BOOKING1, testee.getLastBooking());
	}

	@Test
	public void testAddBooking2Bookings() {
		testee.addBooking(BOOKING1);
		testee.addBooking(BOOKING2);
		assertEquals(2, testee.getBookings().size());
		assertEquals(BOOKING2, testee.getLastBooking());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBookingOpenEnded() {
		testee.addBooking(BOOKING3);
		testee.addBooking(BOOKING1);
	}

	@Test
	public void testRemoveBooking() {
		testee.addBooking(BOOKING1);
		testee.addBooking(BOOKING2);
		testee.removeBooking(BOOKING2);
		assertEquals(1, testee.getBookings().size());
		assertEquals(BOOKING1, testee.getLastBooking());
	}

	@Test
	public void testReplaceBooking() {
		testee.addBooking(BOOKING2);
		testee.addBooking(BOOKING1);
		testee.replaceBooking(BOOKING2, BOOKING4);
		assertEquals(2, testee.getBookings().size());
		assertEquals(BOOKING1, testee.getLastBooking());
		assertEquals(BOOKING4, testee.getBookings().get(0));
	}

	@Test(expected = IllegalStateException.class)
	public void testReplaceBookingNewBookingWithoutEndtimeButNotLast() {
		testee.addBooking(BOOKING2);
		testee.addBooking(BOOKING1);
		testee.replaceBooking(BOOKING2, BOOKING3);
	}
}
