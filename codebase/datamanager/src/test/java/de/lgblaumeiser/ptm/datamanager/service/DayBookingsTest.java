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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;

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
	private static final @NonNull LocalTime TIME2 = LocalTime.of(12, 15);
	@SuppressWarnings("null")
	private static final @NonNull LocalTime TIME3 = LocalTime.of(13, 45);
	private static final @NonNull Activity ACTIVITY1 = Activity.newLineActivity("a", "b");
	private static final @NonNull Activity ACTIVITY2 = Activity.newProjectActivity("a1", "c");
	private static final @NonNull Booking BOOKING1 = Booking.newBooking(TIME1, ACTIVITY1);
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
		assertTrue(Iterables.getFirst(testee.getBookings(), BOOKING1).hasEndtime());
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
		Booking testBooking = testee.addBooking(ACTIVITY1, TIME2);
		testee.endBooking(testBooking, TIME1);
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
		testee.removeBooking(Iterables.getFirst(testee.getBookings(), BOOKING1));
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
		Booking testBooking = testee.addBooking(ACTIVITY1, TIME1);
		testee.changeActivity(testBooking, ACTIVITY1);
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
		Booking testBooking = testee.addBooking(ACTIVITY1, TIME1);
		testee.changeComment(testBooking, StringUtils.EMPTY);
	}
	
	@SuppressWarnings("null")
	@Test(expected = IllegalStateException.class)
	public void testChangeCommentTwiceTheSame() {
		Booking testBooking = testee.addBooking(ACTIVITY1, TIME1);
		testee.changeComment(testBooking, COMMENT1);		
		testee.changeComment(testee.getLastBooking(), COMMENT1);
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
		testee.addBooking(ACTIVITY1, TIME1);
		testee.deleteComment(testee.getLastBooking());		
	}
}
