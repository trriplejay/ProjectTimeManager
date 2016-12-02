/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import org.junit.Test;

/**
 * Test of the Booking class
 */
public class BookingTest {
	private static final LocalTime TIME1 = LocalTime.of(12, 34);
	private static final LocalTime TIME2 = LocalTime.of(13, 57);
	private static final long DIFF = 83;

	private static final Activity ACT1 = Activity.newLineActivity("Act1", "0815");

	/**
	 * Positive test method for newBooking
	 */
	@Test
	public final void testNewBookingLocalTimeActivity() {
		Booking testee = Booking.newBooking().setStarttime(TIME1).setActivity(ACT1).build();
		assertEquals(ACT1, testee.getActivity());
		assertEquals(TIME1, testee.getStarttime());
		assertFalse(testee.hasEndtime());
	}

	/**
	 * Positive test method for newBooking with comment
	 */
	@Test
	public final void testNewBookingLocalTimeActivityString() {
		Booking testee = Booking.newBooking().setStarttime(TIME1).setActivity(ACT1).build();
		assertEquals(ACT1, testee.getActivity());
		assertEquals(TIME1, testee.getStarttime());
		assertFalse(testee.hasEndtime());
	}

	/**
	 * Positive test method for endBooking
	 */
	@Test
	public final void testEndBooking() {
		Booking startBooking = Booking.newBooking().setStarttime(TIME1).setActivity(ACT1).build();
		Booking testee = startBooking.changeBooking().setEndtime(TIME2).build();
		assertEquals(ACT1, testee.getActivity());
		assertEquals(TIME1, testee.getStarttime());
		assertTrue(testee.hasEndtime());
		assertEquals(TIME2, testee.getEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public final void testEndBookingNegative() {
		Booking.newBooking().setStarttime(TIME2).setEndtime(TIME1).setActivity(ACT1).build();
	}

	@Test(expected = NullPointerException.class)
	public final void testNoStarttime() {
		Booking.newBooking().setActivity(ACT1).build();
	}

	@Test(expected = NullPointerException.class)
	public final void testNoActivity() {
		Booking.newBooking().setStarttime(TIME1).setEndtime(TIME2).build();
	}

	/**
	 * Test method for calculate time span
	 */
	@Test
	public final void testCalculateTimeSpan() {
		Booking booking = Booking.newBooking().setStarttime(TIME1).setEndtime(TIME2).setActivity(ACT1)
				.build();
		TimeSpan testee = booking.calculateTimeSpan();
		assertEquals(DIFF, testee.getLengthInMinutes().toMinutes());
	}

}
