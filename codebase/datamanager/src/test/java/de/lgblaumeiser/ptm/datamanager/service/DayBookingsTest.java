/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
    private static final Activity ACTIVITY1 = Activity.newLineActivity("a", "b");
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
	fail("Should not be reached");
    }

    @Test(expected = IllegalStateException.class)
    public void testAddBooking0Empty() {
	testee.addBooking(ACTIVITY1);
    }

    @Test
    public void testAddBooking1Empty() {
	Booking booking = testee.addBooking(ACTIVITY1, TIME1);
	assertFalse(testee.getBookings().isEmpty());
	assertEquals(1, testee.getBookings().size());
	assertEquals(booking, testee.getBookings().get(0));
	assertFalse(testee.getBookings().get(0).hasEndtime());
    }

    @Test
    public void testEndBooking() {
	Booking booking = testee.addBooking(ACTIVITY1, TIME1);
	Booking result = testee.endBooking(booking, TIME2);
	assertFalse(testee.getBookings().isEmpty());
	assertEquals(1, testee.getBookings().size());
	assertEquals(result, testee.getBookings().get(0));
	assertTrue(testee.getBookings().get(0).hasEndtime());
    }
}
