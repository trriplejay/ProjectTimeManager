/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static de.lgblaumeiser.ptm.datamanager.model.Booking.newBooking;
import static java.time.LocalDate.now;
import static java.time.LocalTime.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Test of the Booking class
 */
public class BookingTest {
	private static final LocalDate DATE = now();
	private static final LocalTime TIME1 = of(12, 34);
	private static final LocalTime TIME2 = of(13, 57);
	private static final String USER = "TestUser";
	private static final String COMMENT = "Test Comment";
	private static final long DIFF = 83;

	private static final Activity ACT1 = newActivity("Act1", "0815");

	/**
	 * Positive test method for newBooking
	 */
	@Test
	public final void testNewBookingLocalTimeActivity() {
		Booking testee = newBooking().setBookingday(DATE).setUser(USER).setStarttime(TIME1).setActivity(ACT1).build();
		assertEquals(ACT1, testee.getActivity());
		assertEquals(TIME1, testee.getStarttime());
		assertFalse(testee.hasEndtime());
	}

	/**
	 * Positive test method for newBooking with comment
	 */
	@Test
	public final void testNewBookingLocalTimeActivityString() {
		Booking testee = newBooking().setBookingday(DATE).setUser(USER).setStarttime(TIME1).setActivity(ACT1).build();
		assertEquals(ACT1, testee.getActivity());
		assertEquals(TIME1, testee.getStarttime());
		assertFalse(testee.hasEndtime());
	}

	/**
	 * Positive test method for endBooking
	 */
	@Test
	public final void testEndBooking() {
		Booking startBooking = newBooking().setBookingday(DATE).setUser(USER).setStarttime(TIME1).setActivity(ACT1).build();
		Booking testee = startBooking.changeBooking().setEndtime(TIME2).build();
		assertEquals(ACT1, testee.getActivity());
		assertEquals(TIME1, testee.getStarttime());
		assertTrue(testee.hasEndtime());
		assertEquals(TIME2, testee.getEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public final void testEndBookingNegative() {
		newBooking().setBookingday(DATE).setUser(USER).setStarttime(TIME2).setEndtime(TIME1).setActivity(ACT1).build();
	}

	@Test(expected = IllegalStateException.class)
	public final void testNoStarttime() {
		newBooking().setBookingday(DATE).setUser(USER).setActivity(ACT1).build();
	}

	@Test(expected = IllegalStateException.class)
	public final void testNoActivity() {
		newBooking().setBookingday(DATE).setUser(USER).setStarttime(TIME1).setEndtime(TIME2).build();
	}

	@Test(expected = IllegalStateException.class)
	public final void testNoBookingday() {
		newBooking().setUser(USER).setStarttime(TIME1).setEndtime(TIME2).build();
	}

	@Test(expected = IllegalStateException.class)
	public final void testNoUser() { newBooking().setBookingday(DATE).setStarttime(TIME1).setEndtime(TIME2).build(); }

	@Test
	public final void testNoComment() {
		Booking booking = newBooking().setBookingday(DATE).setUser(USER).setStarttime(TIME1).setActivity(ACT1).build();
		assertEquals(StringUtils.EMPTY, booking.getComment());
	}

	@Test
	public final void testComment() {
		Booking booking = newBooking().setBookingday(DATE).setUser(USER).setStarttime(TIME1).setActivity(ACT1).setComment(COMMENT).build();
		assertEquals(COMMENT, booking.getComment());
	}

	/**
	 * Test method for calculate time span
	 */
	@Test
	public final void testCalculateTimeSpan() {
		Booking booking = newBooking().setBookingday(DATE).setUser(USER).setStarttime(TIME1).setEndtime(TIME2).setActivity(ACT1)
				.build();
		TimeSpan testee = booking.calculateTimeSpan();
		assertEquals(DIFF, testee.getLengthInMinutes().toMinutes());
	}

}
