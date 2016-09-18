/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
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
    private static final LocalTime TIME1A = LocalTime.of(10, 30);
    private static final LocalTime TIME2 = LocalTime.of(12, 15);
    private static final LocalTime TIME3 = LocalTime.of(13, 45);
    private static final LocalTime TIME3A = LocalTime.of(14, 00);
    private static final LocalTime TIME4 = LocalTime.of(15, 35);
    private static final Activity ACTIVITY1 = Activity.newLineActivity("a", "b");
    private static final Activity ACTIVITY2 = Activity.newLineActivity("a1", "c");
    private static final Booking BOOKING1 = Booking.newBooking().setStarttime(TIME1).setActivity(ACTIVITY1).build();
    private static final String COMMENT1 = "My Comment";

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

    @Test
    public void testChangeActivity() {
	Booking testBooking = testee.addBooking(testBookings, ACTIVITY1, TIME1);
	testee.changeActivity(testBookings, testBooking, ACTIVITY2);
	assertEquals(1, testBookings.getBookings().size());
	assertEquals(ACTIVITY2, testBookings.getLastBooking().getActivity());
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeActivityWithUnknownBooking() {
	testee.changeActivity(testBookings, BOOKING1, ACTIVITY2);
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeActivityWithSameActivity() {
	try {
	    Booking testBooking = testee.addBooking(testBookings, ACTIVITY1, TIME1);
	    testee.changeActivity(testBookings, testBooking, ACTIVITY1);
	} finally {
	    assertFalse(testBookings.getBookings().isEmpty());
	}
    }

    @Test
    public void testChangeComment() {
	Booking testBooking = testee.addBooking(testBookings, ACTIVITY1, TIME1);
	testee.changeComment(testBookings, testBooking, COMMENT1);
	assertEquals(COMMENT1, testBookings.getLastBooking().getComment());
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeCommentWithUnknownBooking() {
	testee.changeComment(testBookings, BOOKING1, COMMENT1);
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeCommentWithEmptyComment() {
	try {
	    Booking testBooking = testee.addBooking(testBookings, ACTIVITY1, TIME1);
	    testee.changeComment(testBookings, testBooking, StringUtils.EMPTY);
	} finally {
	    assertFalse(testBookings.getBookings().isEmpty());
	}
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeCommentTwiceTheSame() {
	try {
	    Booking testBooking = testee.addBooking(testBookings, ACTIVITY1, TIME1);
	    testee.changeComment(testBookings, testBooking, COMMENT1);
	    testee.changeComment(testBookings, testBookings.getLastBooking(), COMMENT1);
	} finally {
	    assertFalse(testBookings.getBookings().isEmpty());
	}
    }

    @Test
    public void testDeleteComment() {
	testee.addBooking(testBookings, ACTIVITY1, TIME1);
	testee.changeComment(testBookings, testBookings.getLastBooking(), COMMENT1);
	assertEquals(COMMENT1, testBookings.getLastBooking().getComment());
	testee.deleteComment(testBookings, testBookings.getLastBooking());
	assertTrue(StringUtils.isBlank(testBookings.getLastBooking().getComment()));
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteCommentWithUnknownBooking() {
	testee.deleteComment(testBookings, BOOKING1);
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteCommentAlthoughNoComment() {
	try {
	    testee.addBooking(testBookings, ACTIVITY1, TIME1);
	    testee.deleteComment(testBookings, testBookings.getLastBooking());
	} finally {
	    assertFalse(testBookings.getBookings().isEmpty());
	}
    }

    @Test
    public void testSplitBooking() {
	testee.addBooking(testBookings, ACTIVITY1, TIME1);
	testee.endBooking(testBookings, testBookings.getLastBooking(), TIME3);
	testee.splitBooking(testBookings, testBookings.getLastBooking(), TIME2);
	assertEquals(2, testBookings.getBookings().size());
	assertEquals(TIME2, testBookings.getLastBooking().getStarttime());
	assertEquals(TIME3, testBookings.getLastBooking().getEndtime());
	Booking firstOne = testBookings.getBookings().get(0);
	assertEquals(TIME1, firstOne.getStarttime());
	assertEquals(TIME2, firstOne.getEndtime());
    }

    @Test
    public void testSplitBookingNoEndtime() {
	testee.addBooking(testBookings, ACTIVITY1, TIME1);
	testee.splitBooking(testBookings, testBookings.getLastBooking(), TIME2);
	assertEquals(2, testBookings.getBookings().size());
	assertEquals(TIME2, testBookings.getLastBooking().getStarttime());
	assertFalse(testBookings.getLastBooking().hasEndtime());
	Booking firstOne = testBookings.getBookings().get(0);
	assertEquals(TIME1, firstOne.getStarttime());
	assertEquals(TIME2, firstOne.getEndtime());
    }

    @Test(expected = IllegalStateException.class)
    public void testSplitBookingUnknownBooking() {
	testee.splitBooking(testBookings, BOOKING1, TIME2);
    }

    @Test(expected = IllegalStateException.class)
    public void testSplitBookingWithEndtimeSplittimeToEarly() {
	try {
	    testee.addBooking(testBookings, ACTIVITY1, TIME2);
	    testee.endBooking(testBookings, testBookings.getLastBooking(), TIME3);
	    testee.splitBooking(testBookings, testBookings.getLastBooking(), TIME1);
	} finally {
	    assertEquals(1, testBookings.getBookings().size());
	    assertEquals(TIME2, testBookings.getLastBooking().getStarttime());
	    assertEquals(TIME3, testBookings.getLastBooking().getEndtime());
	}
    }

    @Test(expected = IllegalStateException.class)
    public void testSplitBookingWithEndtimeSplittimeToLate() {
	try {
	    testee.addBooking(testBookings, ACTIVITY1, TIME1);
	    testee.endBooking(testBookings, testBookings.getLastBooking(), TIME2);
	    testee.splitBooking(testBookings, testBookings.getLastBooking(), TIME3);
	} finally {
	    assertEquals(1, testBookings.getBookings().size());
	    assertEquals(TIME1, testBookings.getLastBooking().getStarttime());
	    assertEquals(TIME2, testBookings.getLastBooking().getEndtime());
	}
    }

    @Test(expected = IllegalStateException.class)
    public void testSplitBookingWithoutEndtimeSplittimeToEarly() {
	try {
	    testee.addBooking(testBookings, ACTIVITY1, TIME2);
	    testee.splitBooking(testBookings, testBookings.getLastBooking(), TIME1);
	} finally {
	    assertEquals(1, testBookings.getBookings().size());
	    assertEquals(TIME2, testBookings.getLastBooking().getStarttime());
	    assertFalse(testBookings.getLastBooking().hasEndtime());
	}
    }

    @Test
    public void testChangeBookingTimes() {
	testee.addBooking(testBookings, ACTIVITY1, TIME1);
	testee.addBooking(testBookings, ACTIVITY2, TIME2);
	testee.addBooking(testBookings, ACTIVITY1, TIME3);
	testee.endBooking(testBookings, testBookings.getLastBooking(), TIME4);
	testee.changeBookingTimes(testBookings, testBookings.getBookings().get(1), TIME1A, TIME3A);
	assertEquals(3, testBookings.getBookings().size());
	assertEquals(TIME1A, testBookings.getBookings().get(1).getStarttime());
	assertEquals(TIME3A, testBookings.getBookings().get(1).getEndtime());
	assertEquals(TIME1, testBookings.getBookings().get(0).getStarttime());
	assertEquals(TIME1A, testBookings.getBookings().get(0).getEndtime());
	assertEquals(TIME4, testBookings.getBookings().get(2).getEndtime());
	assertEquals(TIME3A, testBookings.getBookings().get(2).getStarttime());
    }

    @Test
    public void testChangeBookingTimesSuccessiveNoEndtime() {
	testee.addBooking(testBookings, ACTIVITY1, TIME1);
	testee.addBooking(testBookings, ACTIVITY2, TIME2);
	testee.addBooking(testBookings, ACTIVITY1, TIME3);
	testee.changeBookingTimes(testBookings, testBookings.getBookings().get(1), TIME1A, TIME3A);
	assertEquals(3, testBookings.getBookings().size());
	assertEquals(TIME1A, testBookings.getBookings().get(1).getStarttime());
	assertEquals(TIME3A, testBookings.getBookings().get(1).getEndtime());
	assertEquals(TIME1, testBookings.getBookings().get(0).getStarttime());
	assertEquals(TIME1A, testBookings.getBookings().get(0).getEndtime());
	assertFalse(testBookings.getBookings().get(2).hasEndtime());
	assertEquals(TIME3A, testBookings.getBookings().get(2).getStarttime());
    }

    @Test
    public void testChangeBookingTimesNoPrevious() {
	testee.addBooking(testBookings, ACTIVITY2, TIME2);
	testee.addBooking(testBookings, ACTIVITY1, TIME3);
	testee.changeBookingTimes(testBookings, testBookings.getBookings().get(0), TIME1A, TIME3A);
	assertEquals(2, testBookings.getBookings().size());
	assertEquals(TIME1A, testBookings.getBookings().get(0).getStarttime());
	assertEquals(TIME3A, testBookings.getBookings().get(0).getEndtime());
	assertFalse(testBookings.getBookings().get(1).hasEndtime());
	assertEquals(TIME3A, testBookings.getBookings().get(1).getStarttime());
    }

    @Test
    public void testChangeBookingTimesNoSuccessive() {
	testee.addBooking(testBookings, ACTIVITY2, TIME2);
	testee.addBooking(testBookings, ACTIVITY1, TIME3);
	testee.endBooking(testBookings, testBookings.getLastBooking(), TIME4);
	testee.changeBookingTimes(testBookings, testBookings.getBookings().get(1), TIME3A, TIME4);
	assertEquals(2, testBookings.getBookings().size());
	assertEquals(TIME3A, testBookings.getBookings().get(1).getStarttime());
	assertEquals(TIME4, testBookings.getBookings().get(1).getEndtime());
	assertEquals(TIME2, testBookings.getBookings().get(0).getStarttime());
	assertEquals(TIME3A, testBookings.getBookings().get(0).getEndtime());
    }

    @Test
    public void testChangeBookingTimesIntervalSmaller() {
	testee.addBooking(testBookings, ACTIVITY1, TIME1);
	testee.addBooking(testBookings, ACTIVITY2, TIME1A);
	testee.addBooking(testBookings, ACTIVITY1, TIME3A);
	testee.endBooking(testBookings, testBookings.getLastBooking(), TIME4);
	testee.changeBookingTimes(testBookings, testBookings.getBookings().get(1), TIME2, TIME3);
	assertEquals(3, testBookings.getBookings().size());
	assertEquals(TIME2, testBookings.getBookings().get(1).getStarttime());
	assertEquals(TIME3, testBookings.getBookings().get(1).getEndtime());
	assertEquals(TIME1, testBookings.getBookings().get(0).getStarttime());
	assertEquals(TIME2, testBookings.getBookings().get(0).getEndtime());
	assertEquals(TIME4, testBookings.getBookings().get(2).getEndtime());
	assertEquals(TIME3, testBookings.getBookings().get(2).getStarttime());

    }

    @Test(expected = IllegalStateException.class)
    public void testChangeBookingTimesUnknownBooking() {
	testee.changeBookingTimes(testBookings, BOOKING1, TIME2, TIME3);
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeBookingTimesWrongTimeSpan() {
	try {
	    testee.addBooking(testBookings, ACTIVITY1, TIME1);
	    testee.endBooking(testBookings, testBookings.getLastBooking(), TIME2);
	    testee.changeBookingTimes(testBookings, testBookings.getLastBooking(), TIME3, TIME1);
	} finally {
	    assertEquals(1, testBookings.getBookings().size());
	    assertEquals(TIME1, testBookings.getLastBooking().getStarttime());
	    assertEquals(TIME2, testBookings.getLastBooking().getEndtime());
	}
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeBookingTimesToEarlyStarttime() {
	try {
	    testee.addBooking(testBookings, ACTIVITY1, TIME2);
	    testee.addBooking(testBookings, ACTIVITY2, TIME3);
	    testee.endBooking(testBookings, testBookings.getLastBooking(), TIME4);
	    testee.changeBookingTimes(testBookings, testBookings.getLastBooking(), TIME1, TIME3A);
	} finally {
	    assertEquals(2, testBookings.getBookings().size());
	    assertEquals(TIME2, testBookings.getBookings().get(0).getStarttime());
	    assertEquals(TIME3, testBookings.getBookings().get(0).getEndtime());
	    assertEquals(TIME3, testBookings.getBookings().get(1).getStarttime());
	    assertEquals(TIME4, testBookings.getBookings().get(1).getEndtime());
	}
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeBookingTimesToLateEndtime() {
	try {
	    testee.addBooking(testBookings, ACTIVITY1, TIME1);
	    testee.addBooking(testBookings, ACTIVITY2, TIME2);
	    testee.endBooking(testBookings, testBookings.getLastBooking(), TIME3);
	    testee.changeBookingTimes(testBookings, testBookings.getBookings().get(0), TIME1, TIME4);
	} finally {
	    assertEquals(2, testBookings.getBookings().size());
	    assertEquals(TIME1, testBookings.getBookings().get(0).getStarttime());
	    assertEquals(TIME2, testBookings.getBookings().get(0).getEndtime());
	    assertEquals(TIME2, testBookings.getBookings().get(1).getStarttime());
	    assertEquals(TIME3, testBookings.getBookings().get(1).getEndtime());
	}
    }
}
