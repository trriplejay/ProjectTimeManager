/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * Tests for the DayBookings service
 */
public class DayBookingsTest {
    private static final LocalDate DATE1 = LocalDate.of(2016, 06, 24);
    private static final LocalTime TIME1 = LocalTime.of(10, 12);
    private static final LocalTime TIME2 = LocalTime.of(12, 15);
    private static final LocalTime TIME3 = LocalTime.of(13, 45);
    private static final Activity ACTIVITY1 = Activity.newLineActivity("a", "b");
    private static final Activity ACTIVITY2 = Activity.newProjectActivity("a1", "c");
    private static final Booking BOOKING1 = Booking.newBooking(TIME1, ACTIVITY1);

    private DayBookings testee;

    @Before
    public void before() {
	testee = DayBookings.createDayBookings(DATE1);
    }

    @Test
    public void testCreateDayBookings() {
	assertEquals(DATE1, testee.getDay());
	assertTrue(testee.getBookings().isEmpty());
    }

    /**
     * Test unmodifiability of booking list
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testBookingListUnmodifiability() {
	testee.getBookings().add(BOOKING1);
    }

    @Test
    public void testGetLastBooking() {
	assertNull(testee.getLastBooking());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddBooking0Empty() {
	testee.addBooking(ACTIVITY1, null);
    }

    @Test
    public void testAddBooking0Positive() {
	Booking firstOne = testee.addBooking(ACTIVITY1, TIME1, null);
	testee.endBooking(firstOne, TIME2);
	Booking testBooking = testee.addBooking(ACTIVITY2, null);
	assertFalse(testee.getBookings().isEmpty());
	assertEquals(2, testee.getBookings().size());
	assertEquals(testBooking, testee.getLastBooking());
	assertEquals(TIME2, testee.getLastBooking().getStarttime());
	assertFalse(testee.getLastBooking().hasEndtime());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddBooking0NoEndtime() {
	testee.addBooking(ACTIVITY1, TIME1, null);
	testee.addBooking(ACTIVITY2, null);
    }

    @Test
    public void testAddBooking1Empty() {
	Booking booking = testee.addBooking(ACTIVITY1, TIME1, null);
	assertFalse(testee.getBookings().isEmpty());
	assertEquals(1, testee.getBookings().size());
	assertEquals(booking, testee.getLastBooking());
	assertFalse(testee.getLastBooking().hasEndtime());
    }

    @Test
    public void testAddBooking1With2Bookings() {
	testee.addBooking(ACTIVITY1, TIME1, null);
	Booking testBooking = testee.addBooking(ACTIVITY2, TIME2, null);
	assertEquals(2, testee.getBookings().size());
	assertEquals(testBooking, testee.getLastBooking());
	assertFalse(testee.getLastBooking().hasEndtime());
	assertTrue(testee.getBookings().get(0).hasEndtime());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddBooking1WithEndedLastBooking() {
	Booking firstOne = testee.addBooking(ACTIVITY1, TIME1, null);
	testee.endBooking(firstOne, TIME2);
	testee.addBooking(ACTIVITY2, TIME3, null);
    }

    @Test
    public void testEndBooking() {
	Booking booking = testee.addBooking(ACTIVITY1, TIME1, null);
	Booking result = testee.endBooking(booking, TIME2);
	assertFalse(testee.getBookings().isEmpty());
	assertEquals(1, testee.getBookings().size());
	assertEquals(result, testee.getLastBooking());
	assertTrue(testee.getLastBooking().hasEndtime());
    }

    @Test(expected = IllegalStateException.class)
    public void testEndBookingSecondEnd() {
	Booking booking = testee.addBooking(ACTIVITY1, TIME1, null);
	Booking endedBooking = testee.endBooking(booking, TIME2);
	testee.endBooking(endedBooking, TIME3);
    }

    @Test(expected = IllegalStateException.class)
    public void testEndBookingWrongBookingTime() {
	Booking testBooking = testee.addBooking(ACTIVITY1, TIME2, null);
	testee.endBooking(testBooking, TIME1);
    }
}