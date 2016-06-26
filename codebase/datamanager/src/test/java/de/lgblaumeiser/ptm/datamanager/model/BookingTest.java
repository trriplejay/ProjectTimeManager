/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

/**
 * Test of the Booking class
 */
public class BookingTest {
    @SuppressWarnings("null")
    @NonNull
    private static final LocalTime TIME1 = LocalTime.of(12, 34);
    @SuppressWarnings("null")
    @NonNull
    private static final LocalTime TIME2 = LocalTime.of(13, 57);

    @NonNull
    private static final Activity ACT1 = Activity.newLineActivity("Cat1", "0815");

    @NonNull
    private static final String COMMENT1 = "Test Comment\nTest Line 2";

    /**
     * Positive test method for newBooking
     */
    @Test
    public final void testNewBookingLocalTimeActivity() {
	Booking testee = Booking.newBooking(TIME1, ACT1);
	assertEquals(ACT1, testee.getActivity());
	assertEquals(TIME1, testee.getStarttime());
	assertFalse(testee.hasEndtime());
	assertTrue(StringUtils.isBlank(testee.getComment()));
    }

    /**
     * Positive test method for newBooking with comment
     */
    @Test
    public final void testNewBookingLocalTimeActivityString() {
	Booking testee = Booking.newBooking(TIME1, ACT1, COMMENT1);
	assertEquals(ACT1, testee.getActivity());
	assertEquals(TIME1, testee.getStarttime());
	assertFalse(testee.hasEndtime());
	assertEquals(COMMENT1, testee.getComment());
    }

    /**
     * Positive test method for endBooking
     */
    @Test
    public final void testEndBooking() {
	Booking startBooking = Booking.newBooking(TIME1, ACT1, COMMENT1);
	Booking testee = Booking.endBooking(startBooking, TIME2);
	assertEquals(ACT1, testee.getActivity());
	assertEquals(TIME1, testee.getStarttime());
	assertTrue(testee.hasEndtime());
	assertEquals(TIME2, testee.getEndtime());
	assertEquals(COMMENT1, testee.getComment());
    }

    @Test(expected = IllegalStateException.class)
    public final void testEndBookingNegative() {
	Booking startBooking = Booking.newBooking(TIME2, ACT1, COMMENT1);
	Booking.endBooking(startBooking, TIME1);
    }

    /**
     * Test method for calculate time span
     */
    @Test
    public final void testCalculateTimeSpan() {
	Booking startBooking = Booking.newBooking(TIME1, ACT1, COMMENT1);
	Booking endBooking = Booking.endBooking(startBooking, TIME2);
	TimeSpan testee = endBooking.calculateTimeSpan();
	assertEquals(TIME1, testee.getStarttime());
	assertEquals(TIME2, testee.getEndtime());
    }

}
