/*

 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>

 */
package de.lgblaumeiser.ptm.datamanager.service;

import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Lists.newArrayList;
import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static java.util.Collections.unmodifiableCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * Tests for the DayBookings service
 */
public class BookingServiceTest {
	private static final LocalDate DATE1 = LocalDate.of(2016, 06, 24);
	private static final LocalTime TIME1 = LocalTime.of(10, 12);
	private static final LocalTime TIME2 = LocalTime.of(12, 15);
	private static final LocalTime TIME3 = LocalTime.of(13, 45);
	private static final Activity ACTIVITY1 = newActivity("a", "b");
	private static final Activity ACTIVITY2 = newActivity("a1", "c");
	private BookingService testee;
	private ObjectStore<Booking> mockStore;

	@Before
	public void before() {
		mockStore = new ObjectStore<Booking>() {
			private static final String ID = "id";

			private Collection<Booking> storedBookings = newArrayList();
			private Long id = 1L;

			@Override
			public Collection<Booking> retrieveAll() {
				return unmodifiableCollection(storedBookings);
			}

			@Override
			public Optional<Booking> retrieveById(Long id) {
				return storedBookings.stream().filter(b -> id == b.getId()).findFirst();
			}

			@Override
			public Booking store(Booking object) {
				deleteById(object.getId());
				storedBookings.stream().filter(b -> id == b.getId()).findFirst().ifPresent(storedBookings::remove);
				storedBookings.add(object);
				Long currentId = object.getId();
				if (currentId == -1) {
					try {
						Field f = object.getClass().getDeclaredField(ID);
						f.setAccessible(true);
						f.set(object, id);
						id++;
						f.setAccessible(false);
					} catch (IllegalAccessException | IllegalArgumentException | ClassCastException
							| NoSuchFieldException | SecurityException e) {
						throw new IllegalStateException(e);
					}
				}
				return object;
			}

			@Override
			public void deleteById(Long id) {
				storedBookings.stream().filter(b -> id == b.getId()).findFirst().ifPresent(storedBookings::remove);
			}
		};
		testee = new BookingServiceImpl().setBookingStore(mockStore);
	}

	@Test
	public void testAddBooking1Empty() {
		Booking booking = testee.addBooking(DATE1, ACTIVITY1, TIME1);
		assertEquals(1, mockStore.retrieveAll().size());
		assertEquals(booking, get(mockStore.retrieveAll(), 0));
	}

	@Test
	public void testAddBooking1With2Bookings() {
		testee.addBooking(DATE1, ACTIVITY1, TIME1);
		Booking testBooking = testee.addBooking(DATE1, ACTIVITY2, TIME2);
		assertEquals(2, mockStore.retrieveAll().size());
		assertEquals(testBooking, get(mockStore.retrieveAll(), 1));
		assertTrue(get(mockStore.retrieveAll(), 0).hasEndtime());
	}

	@Test
	public void testAddBooking1WithEndedLastBooking() {
		Booking firstOne = testee.addBooking(DATE1, ACTIVITY1, TIME1);
		testee.endBooking(firstOne, TIME2);
		Booking secondOne = testee.addBooking(DATE1, ACTIVITY2, TIME3);
		assertEquals(2, mockStore.retrieveAll().size());
		assertEquals(secondOne, get(mockStore.retrieveAll(), 1));
		assertTrue(get(mockStore.retrieveAll(), 0).hasEndtime());
		assertEquals(TIME2, get(mockStore.retrieveAll(), 0).getEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBooking1WithSameStarttime() {
		testee.addBooking(DATE1, ACTIVITY1, TIME1);
		testee.addBooking(DATE1, ACTIVITY2, TIME1);
	}

	@Test
	public void testEndBooking() {
		Booking booking = testee.addBooking(DATE1, ACTIVITY1, TIME1);
		Booking result = testee.endBooking(booking, TIME2);
		assertEquals(1, mockStore.retrieveAll().size());
		assertEquals(result, get(mockStore.retrieveAll(), 0));
	}

	@Test
	public void testEndBookingSecondEnd() {
		Booking booking = testee.addBooking(DATE1, ACTIVITY1, TIME1);
		Booking endedBooking = testee.endBooking(booking, TIME2);
		Booking testBooking = testee.endBooking(endedBooking, TIME3);
		assertTrue(testBooking.hasEndtime());
		assertEquals(TIME3, testBooking.getEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public void testEndBookingWrongBookingTime() {
		try {
			Booking testBooking = testee.addBooking(DATE1, ACTIVITY1, TIME2);
			testee.endBooking(testBooking, TIME1);
		} finally {
			assertFalse(mockStore.retrieveAll().isEmpty());
		}
	}
}
