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
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * Tests for the DayBookings service
 */
public class DayBookingsTest {
    @SuppressWarnings("null")
    private static final @NonNull LocalDate DATE1 = LocalDate.of(2016, 06, 24);
    @SuppressWarnings("null")
    private static final @NonNull LocalTime TIME1 = LocalTime.of(10, 12);
    @SuppressWarnings("null")
    private static final @NonNull LocalTime TIME1A = LocalTime.of(10, 30);
    @SuppressWarnings("null")
    private static final @NonNull LocalTime TIME2 = LocalTime.of(12, 15);
    @SuppressWarnings("null")
    private static final @NonNull LocalTime TIME3 = LocalTime.of(13, 45);
    @SuppressWarnings("null")
    private static final @NonNull LocalTime TIME3A = LocalTime.of(14, 00);
    @SuppressWarnings("null")
    private static final @NonNull LocalTime TIME4 = LocalTime.of(15, 35);
    private static final @NonNull Activity ACTIVITY1 = Activity.newActivity().setCategoryId("a").setBookingNumber("b")
	    .setLineActivity().build();
    private static final @NonNull Activity ACTIVITY2 = Activity.newActivity().setCategoryId("a1").setBookingNumber("c")
	    .setProjectActivity().build();
    private static final @NonNull Booking BOOKING1 = Booking.newBooking().setStarttime(TIME1).setActivity(ACTIVITY1)
	    .build();
    private static final @NonNull String COMMENT1 = "My Comment";

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
	testee.addBooking(ACTIVITY1);
    }

    @Test
    public void testAddBooking0Positive() {
	Booking firstOne = testee.addBooking(ACTIVITY1, TIME1);
	testee.endBooking(firstOne, TIME2);
	Booking testBooking = testee.addBooking(ACTIVITY2);
	assertFalse(testee.getBookings().isEmpty());
	assertEquals(2, testee.getBookings().size());
	assertEquals(testBooking, testee.getLastBooking());
	assertEquals(TIME2, testee.getLastBooking().getStarttime());
	assertFalse(testee.getLastBooking().hasEndtime());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddBooking0NoEndtime() {
	testee.addBooking(ACTIVITY1, TIME1);
	testee.addBooking(ACTIVITY2);
    }

    @Test
    public void testAddBooking1Empty() {
	Booking booking = testee.addBooking(ACTIVITY1, TIME1);
	assertFalse(testee.getBookings().isEmpty());
	assertEquals(1, testee.getBookings().size());
	assertEquals(booking, testee.getLastBooking());
	assertFalse(testee.getLastBooking().hasEndtime());
    }

    @Test
    public void testAddBooking1With2Bookings() {
	testee.addBooking(ACTIVITY1, TIME1);
	Booking testBooking = testee.addBooking(ACTIVITY2, TIME2);
	assertEquals(2, testee.getBookings().size());
	assertEquals(testBooking, testee.getLastBooking());
	assertFalse(testee.getLastBooking().hasEndtime());
	assertTrue(getBookingWithIndex(testee.getBookings(), 0).hasEndtime());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddBooking1WithEndedLastBooking() {
	Booking firstOne = testee.addBooking(ACTIVITY1, TIME1);
	testee.endBooking(firstOne, TIME2);
	testee.addBooking(ACTIVITY2, TIME3);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddBooking1WithSameStarttime() {
	testee.addBooking(ACTIVITY1, TIME1);
	testee.addBooking(ACTIVITY2, TIME1);
    }

    @Test
    public void testEndBooking() {
	Booking booking = testee.addBooking(ACTIVITY1, TIME1);
	Booking result = testee.endBooking(booking, TIME2);
	assertFalse(testee.getBookings().isEmpty());
	assertEquals(1, testee.getBookings().size());
	assertEquals(result, testee.getLastBooking());
	assertTrue(testee.getLastBooking().hasEndtime());
    }

    @Test(expected = IllegalStateException.class)
    public void testEndBookingSecondEnd() {
	Booking booking = testee.addBooking(ACTIVITY1, TIME1);
	Booking endedBooking = testee.endBooking(booking, TIME2);
	testee.endBooking(endedBooking, TIME3);
    }

    @Test(expected = IllegalStateException.class)
    public void testEndBookingWrongBookingTime() {
	try {
	    Booking testBooking = testee.addBooking(ACTIVITY1, TIME2);
	    testee.endBooking(testBooking, TIME1);
	} finally {
	    assertFalse(testee.getBookings().isEmpty());
	}
    }

    @Test(expected = IllegalStateException.class)
    public void testEndBookingWithAnUnknownBooking() {
	testee.endBooking(BOOKING1, TIME3);
    }

    @Test
    public void testRemoveBooking() {
	Booking testBooking = testee.addBooking(ACTIVITY1, TIME1);
	testee.removeBooking(testBooking);
	assertTrue(testee.getBookings().isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveBookingUnknownBooking() {
	testee.removeBooking(BOOKING1);
    }

    @Test
    public void testRemoveBookingWithPreviousBooking() {
	testee.addBooking(ACTIVITY1, TIME1);
	Booking startBooking = testee.addBooking(ACTIVITY2, TIME2);
	Booking testBooking = testee.endBooking(startBooking, TIME3);
	testee.removeBooking(testBooking);
	assertEquals(1, testee.getBookings().size());
	assertEquals(TIME3, testee.getLastBooking().getEndtime());
	assertEquals(TIME1, testee.getLastBooking().getStarttime());
    }

    @Test
    public void testRemoveBookingWithPreviousBookingButUnendedRemoveCandidate() {
	testee.addBooking(ACTIVITY1, TIME1);
	Booking testBooking = testee.addBooking(ACTIVITY2, TIME2);
	testee.removeBooking(testBooking);
	assertEquals(1, testee.getBookings().size());
	assertEquals(TIME1, testee.getLastBooking().getStarttime());
	assertFalse(testee.getLastBooking().hasEndtime());
    }

    @Test
    public void testRemoveBookingFirst() {
	testee.addBooking(ACTIVITY1, TIME1);
	testee.addBooking(ACTIVITY2, TIME2);
	testee.removeBooking(getBookingWithIndex(testee.getBookings(), 0));
	assertEquals(1, testee.getBookings().size());
	assertEquals(TIME2, testee.getLastBooking().getStarttime());
    }

    @Test
    public void testChangeActivity() {
	Booking testBooking = testee.addBooking(ACTIVITY1, TIME1);
	testee.changeActivity(testBooking, ACTIVITY2);
	assertEquals(1, testee.getBookings().size());
	assertEquals(ACTIVITY2, testee.getLastBooking().getActivity());
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeActivityWithUnknownBooking() {
	testee.changeActivity(BOOKING1, ACTIVITY2);
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeActivityWithSameActivity() {
	try {
	    Booking testBooking = testee.addBooking(ACTIVITY1, TIME1);
	    testee.changeActivity(testBooking, ACTIVITY1);
	} finally {
	    assertFalse(testee.getBookings().isEmpty());
	}
    }

    @Test
    public void testChangeComment() {
	Booking testBooking = testee.addBooking(ACTIVITY1, TIME1);
	testee.changeComment(testBooking, COMMENT1);
	assertEquals(COMMENT1, testee.getLastBooking().getComment());
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeCommentWithUnknownBooking() {
	testee.changeComment(BOOKING1, COMMENT1);
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public void testChangeCommentWithEmptyComment() {
	try {
	    Booking testBooking = testee.addBooking(ACTIVITY1, TIME1);
	    testee.changeComment(testBooking, StringUtils.EMPTY);
	} finally {
	    assertFalse(testee.getBookings().isEmpty());
	}
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public void testChangeCommentTwiceTheSame() {
	try {
	    Booking testBooking = testee.addBooking(ACTIVITY1, TIME1);
	    testee.changeComment(testBooking, COMMENT1);
	    testee.changeComment(testee.getLastBooking(), COMMENT1);
	} finally {
	    assertFalse(testee.getBookings().isEmpty());
	}
    }

    @SuppressWarnings("null")
    @Test
    public void testDeleteComment() {
	testee.addBooking(ACTIVITY1, TIME1);
	testee.changeComment(testee.getLastBooking(), COMMENT1);
	assertEquals(COMMENT1, testee.getLastBooking().getComment());
	testee.deleteComment(testee.getLastBooking());
	assertTrue(StringUtils.isBlank(testee.getLastBooking().getComment()));
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteCommentWithUnknownBooking() {
	testee.deleteComment(BOOKING1);
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public void testDeleteCommentAlthoughNoComment() {
	try {
	    testee.addBooking(ACTIVITY1, TIME1);
	    testee.deleteComment(testee.getLastBooking());
	} finally {
	    assertFalse(testee.getBookings().isEmpty());
	}
    }

    @SuppressWarnings("null")
    @Test
    public void testSplitBooking() {
	testee.addBooking(ACTIVITY1, TIME1);
	testee.endBooking(testee.getLastBooking(), TIME3);
	testee.splitBooking(testee.getLastBooking(), TIME2);
	assertEquals(2, testee.getBookings().size());
	assertEquals(TIME2, testee.getLastBooking().getStarttime());
	assertEquals(TIME3, testee.getLastBooking().getEndtime());
	Booking firstOne = getBookingWithIndex(testee.getBookings(), 0);
	assertEquals(TIME1, firstOne.getStarttime());
	assertEquals(TIME2, firstOne.getEndtime());
    }

    @SuppressWarnings("null")
    @Test
    public void testSplitBookingNoEndtime() {
	testee.addBooking(ACTIVITY1, TIME1);
	testee.splitBooking(testee.getLastBooking(), TIME2);
	assertEquals(2, testee.getBookings().size());
	assertEquals(TIME2, testee.getLastBooking().getStarttime());
	assertFalse(testee.getLastBooking().hasEndtime());
	Booking firstOne = getBookingWithIndex(testee.getBookings(), 0);
	assertEquals(TIME1, firstOne.getStarttime());
	assertEquals(TIME2, firstOne.getEndtime());
    }

    @Test(expected = IllegalStateException.class)
    public void testSplitBookingUnknownBooking() {
	testee.splitBooking(BOOKING1, TIME2);
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public void testSplitBookingWithEndtimeSplittimeToEarly() {
	try {
	    testee.addBooking(ACTIVITY1, TIME2);
	    testee.endBooking(testee.getLastBooking(), TIME3);
	    testee.splitBooking(testee.getLastBooking(), TIME1);
	} finally {
	    assertEquals(1, testee.getBookings().size());
	    assertEquals(TIME2, testee.getLastBooking().getStarttime());
	    assertEquals(TIME3, testee.getLastBooking().getEndtime());
	}
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public void testSplitBookingWithEndtimeSplittimeToLate() {
	try {
	    testee.addBooking(ACTIVITY1, TIME1);
	    testee.endBooking(testee.getLastBooking(), TIME2);
	    testee.splitBooking(testee.getLastBooking(), TIME3);
	} finally {
	    assertEquals(1, testee.getBookings().size());
	    assertEquals(TIME1, testee.getLastBooking().getStarttime());
	    assertEquals(TIME2, testee.getLastBooking().getEndtime());
	}
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public void testSplitBookingWithoutEndtimeSplittimeToEarly() {
	try {
	    testee.addBooking(ACTIVITY1, TIME2);
	    testee.splitBooking(testee.getLastBooking(), TIME1);
	} finally {
	    assertEquals(1, testee.getBookings().size());
	    assertEquals(TIME2, testee.getLastBooking().getStarttime());
	    assertFalse(testee.getLastBooking().hasEndtime());
	}
    }

    @SuppressWarnings("null")
    @Test
    public void testChangeBookingTimes() {
	testee.addBooking(ACTIVITY1, TIME1);
	testee.addBooking(ACTIVITY2, TIME2);
	testee.addBooking(ACTIVITY1, TIME3);
	testee.endBooking(testee.getLastBooking(), TIME4);
	testee.changeBookingTimes(getBookingWithIndex(testee.getBookings(), 1), TIME1A, TIME3A);
	assertEquals(3, testee.getBookings().size());
	assertEquals(TIME1A, getBookingWithIndex(testee.getBookings(), 1).getStarttime());
	assertEquals(TIME3A, getBookingWithIndex(testee.getBookings(), 1).getEndtime());
	assertEquals(TIME1, getBookingWithIndex(testee.getBookings(), 0).getStarttime());
	assertEquals(TIME1A, getBookingWithIndex(testee.getBookings(), 0).getEndtime());
	assertEquals(TIME4, getBookingWithIndex(testee.getBookings(), 2).getEndtime());
	assertEquals(TIME3A, getBookingWithIndex(testee.getBookings(), 2).getStarttime());
    }

    @Test
    public void testChangeBookingTimesSuccessiveNoEndtime() {
	testee.addBooking(ACTIVITY1, TIME1);
	testee.addBooking(ACTIVITY2, TIME2);
	testee.addBooking(ACTIVITY1, TIME3);
	testee.changeBookingTimes(getBookingWithIndex(testee.getBookings(), 1), TIME1A, TIME3A);
	assertEquals(3, testee.getBookings().size());
	assertEquals(TIME1A, getBookingWithIndex(testee.getBookings(), 1).getStarttime());
	assertEquals(TIME3A, getBookingWithIndex(testee.getBookings(), 1).getEndtime());
	assertEquals(TIME1, getBookingWithIndex(testee.getBookings(), 0).getStarttime());
	assertEquals(TIME1A, getBookingWithIndex(testee.getBookings(), 0).getEndtime());
	assertFalse(getBookingWithIndex(testee.getBookings(), 2).hasEndtime());
	assertEquals(TIME3A, getBookingWithIndex(testee.getBookings(), 2).getStarttime());
    }

    @Test
    public void testChangeBookingTimesNoPrevious() {
	testee.addBooking(ACTIVITY2, TIME2);
	testee.addBooking(ACTIVITY1, TIME3);
	testee.changeBookingTimes(getBookingWithIndex(testee.getBookings(), 0), TIME1A, TIME3A);
	assertEquals(2, testee.getBookings().size());
	assertEquals(TIME1A, getBookingWithIndex(testee.getBookings(), 0).getStarttime());
	assertEquals(TIME3A, getBookingWithIndex(testee.getBookings(), 0).getEndtime());
	assertFalse(getBookingWithIndex(testee.getBookings(), 1).hasEndtime());
	assertEquals(TIME3A, getBookingWithIndex(testee.getBookings(), 1).getStarttime());
    }

    @SuppressWarnings("null")
    @Test
    public void testChangeBookingTimesNoSuccessive() {
	testee.addBooking(ACTIVITY2, TIME2);
	testee.addBooking(ACTIVITY1, TIME3);
	testee.endBooking(testee.getLastBooking(), TIME4);
	testee.changeBookingTimes(getBookingWithIndex(testee.getBookings(), 1), TIME3A, TIME4);
	assertEquals(2, testee.getBookings().size());
	assertEquals(TIME3A, getBookingWithIndex(testee.getBookings(), 1).getStarttime());
	assertEquals(TIME4, getBookingWithIndex(testee.getBookings(), 1).getEndtime());
	assertEquals(TIME2, getBookingWithIndex(testee.getBookings(), 0).getStarttime());
	assertEquals(TIME3A, getBookingWithIndex(testee.getBookings(), 0).getEndtime());
    }

    @SuppressWarnings("null")
    @Test
    public void testChangeBookingTimesIntervalSmaller() {
	testee.addBooking(ACTIVITY1, TIME1);
	testee.addBooking(ACTIVITY2, TIME1A);
	testee.addBooking(ACTIVITY1, TIME3A);
	testee.endBooking(testee.getLastBooking(), TIME4);
	testee.changeBookingTimes(getBookingWithIndex(testee.getBookings(), 1), TIME2, TIME3);
	assertEquals(3, testee.getBookings().size());
	assertEquals(TIME2, getBookingWithIndex(testee.getBookings(), 1).getStarttime());
	assertEquals(TIME3, getBookingWithIndex(testee.getBookings(), 1).getEndtime());
	assertEquals(TIME1, getBookingWithIndex(testee.getBookings(), 0).getStarttime());
	assertEquals(TIME2, getBookingWithIndex(testee.getBookings(), 0).getEndtime());
	assertEquals(TIME4, getBookingWithIndex(testee.getBookings(), 2).getEndtime());
	assertEquals(TIME3, getBookingWithIndex(testee.getBookings(), 2).getStarttime());

    }

    @Test(expected = IllegalStateException.class)
    public void testChangeBookingTimesUnknownBooking() {
	testee.changeBookingTimes(BOOKING1, TIME2, TIME3);
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public void testChangeBookingTimesWrongTimeSpan() {
	try {
	    testee.addBooking(ACTIVITY1, TIME1);
	    testee.endBooking(testee.getLastBooking(), TIME2);
	    testee.changeBookingTimes(testee.getLastBooking(), TIME3, TIME1);
	} finally {
	    assertEquals(1, testee.getBookings().size());
	    assertEquals(TIME1, testee.getLastBooking().getStarttime());
	    assertEquals(TIME2, testee.getLastBooking().getEndtime());
	}
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public void testChangeBookingTimesToEarlyStarttime() {
	try {
	    testee.addBooking(ACTIVITY1, TIME2);
	    testee.addBooking(ACTIVITY2, TIME3);
	    testee.endBooking(testee.getLastBooking(), TIME4);
	    testee.changeBookingTimes(testee.getLastBooking(), TIME1, TIME3A);
	} finally {
	    assertEquals(2, testee.getBookings().size());
	    assertEquals(TIME2, getBookingWithIndex(testee.getBookings(), 0).getStarttime());
	    assertEquals(TIME3, getBookingWithIndex(testee.getBookings(), 0).getEndtime());
	    assertEquals(TIME3, getBookingWithIndex(testee.getBookings(), 1).getStarttime());
	    assertEquals(TIME4, getBookingWithIndex(testee.getBookings(), 1).getEndtime());
	}
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public void testChangeBookingTimesToLateEndtime() {
	try {
	    testee.addBooking(ACTIVITY1, TIME1);
	    testee.addBooking(ACTIVITY2, TIME2);
	    testee.endBooking(testee.getLastBooking(), TIME3);
	    testee.changeBookingTimes(getBookingWithIndex(testee.getBookings(), 0), TIME1, TIME4);
	} finally {
	    assertEquals(2, testee.getBookings().size());
	    assertEquals(TIME1, getBookingWithIndex(testee.getBookings(), 0).getStarttime());
	    assertEquals(TIME2, getBookingWithIndex(testee.getBookings(), 0).getEndtime());
	    assertEquals(TIME2, getBookingWithIndex(testee.getBookings(), 1).getStarttime());
	    assertEquals(TIME3, getBookingWithIndex(testee.getBookings(), 1).getEndtime());
	}
    }

    @SuppressWarnings("null")
    private @NonNull Booking getBookingWithIndex(final Collection<Booking> bookings, final int index) {
	List<Booking> tempList = Lists.newArrayList(bookings);
	Preconditions.checkState(bookings.size() > index);
	return tempList.get(index);
    }
}
