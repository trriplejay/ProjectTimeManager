/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.datamanager.service;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Lists.newArrayList;
import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static java.util.Collections.unmodifiableCollection;
import static org.junit.Assert.*;

/**
 * Tests for the DayBookings service
 */
public class BookingServiceTest {
	private static final LocalDate DATE1 = LocalDate.of(2016, 06, 24);
	private static final LocalTime TIME1 = LocalTime.of(10, 12);
	private static final LocalTime TIME2 = LocalTime.of(12, 15);
	private static final LocalTime TIME3 = LocalTime.of(13, 45);
	private static final Activity ACTIVITY1 = newActivity().setActivityName("a").setBookingNumber("b").build();
	private static final Activity ACTIVITY2 = newActivity().setActivityName("a1").setBookingNumber("c").build();
	private static final String USER = "TestUser";
	private static final String COMMENT1 = "Test Comment";
	private static final String COMMENT2 = "";
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
		Booking booking = testee.addBooking(DATE1, USER, ACTIVITY1, TIME1, Optional.empty(), Optional.of(COMMENT1));
		assertEquals(1, mockStore.retrieveAll().size());
		assertEquals(booking, get(mockStore.retrieveAll(), 0));
	}

	@Test
	public void testAddBooking1With2Bookings() {
		testee.addBooking(DATE1, USER, ACTIVITY1, TIME1, Optional.empty(), Optional.empty());
		Booking testBooking = testee.addBooking(DATE1, USER, ACTIVITY2, TIME2, Optional.empty(), Optional.of(COMMENT1));
		assertEquals(2, mockStore.retrieveAll().size());
		assertEquals(testBooking, get(mockStore.retrieveAll(), 1));
		assertTrue(get(mockStore.retrieveAll(), 0).hasEndtime());
	}

	@Test
	public void testAddBooking1WithEndedLastBooking() {
		Booking firstOne = testee.addBooking(DATE1, USER, ACTIVITY1, TIME1, Optional.empty(), Optional.of(COMMENT1));
		testee.changeBooking(firstOne, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(TIME2), Optional.empty());
		Booking secondOne = testee.addBooking(DATE1, USER, ACTIVITY2, TIME3, Optional.empty(), Optional.of(COMMENT2));
		assertEquals(2, mockStore.retrieveAll().size());
		assertEquals(secondOne, get(mockStore.retrieveAll(), 1));
		assertTrue(get(mockStore.retrieveAll(), 0).hasEndtime());
		assertEquals(TIME2, get(mockStore.retrieveAll(), 0).getEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBooking1WithSameStarttime() {
		testee.addBooking(DATE1, USER, ACTIVITY1, TIME1, Optional.empty(), Optional.of(COMMENT1));
		testee.addBooking(DATE1, USER, ACTIVITY2, TIME1, Optional.empty(), Optional.of(COMMENT2));
	}

	@Test
	public void testEndBooking() {
		Booking booking = testee.addBooking(DATE1, USER, ACTIVITY1, TIME1, Optional.empty(), Optional.of(COMMENT1));
		Booking result = testee.changeBooking(booking, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(TIME2), Optional.empty());
		assertEquals(1, mockStore.retrieveAll().size());
		assertEquals(result, get(mockStore.retrieveAll(), 0));
	}

	@Test
	public void testEndBookingSecondEnd() {
		Booking booking = testee.addBooking(DATE1, USER, ACTIVITY1, TIME1, Optional.empty(), Optional.of(COMMENT1));
		Booking endedBooking = testee.changeBooking(booking, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(TIME2), Optional.empty());
		Booking testBooking = testee.changeBooking(endedBooking, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(TIME3), Optional.empty());
		assertTrue(testBooking.hasEndtime());
		assertEquals(TIME3, testBooking.getEndtime());
	}

	@Test(expected = IllegalStateException.class)
	public void testEndBookingWrongBookingTime() {
		try {
			Booking testBooking = testee.addBooking(DATE1, USER, ACTIVITY1, TIME2, Optional.empty(), Optional.of(COMMENT2));
			testee.changeBooking(testBooking, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(TIME1), Optional.empty());
		} finally {
			assertFalse(mockStore.retrieveAll().isEmpty());
		}
	}

	@Test
    public void testCreatBookingWithEndtime() {
        Booking result = testee.addBooking(DATE1, USER, ACTIVITY1, TIME1, Optional.of(TIME2), Optional.of(COMMENT1));
        assertEquals(1, mockStore.retrieveAll().size());
        assertEquals(result, get(mockStore.retrieveAll(), 0));
    }

    @Test
    public void testChangeBooking() {
        Booking first = testee.addBooking(DATE1, USER, ACTIVITY1, TIME1, Optional.of(TIME2), Optional.of(COMMENT1));
	    Booking second = testee.changeBooking(first, Optional.of(DATE1.plusDays(2)), Optional.of(ACTIVITY2),
                Optional.of(TIME2), Optional.of(TIME3), Optional.empty());
        assertEquals(1, mockStore.retrieveAll().size());
        assertEquals(second, get(mockStore.retrieveAll(), 0));
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeBookingWrongBookingTime() {
        try {
            Booking first = testee.addBooking(DATE1, USER, ACTIVITY1, TIME1, Optional.of(TIME2), Optional.of(COMMENT1));
            testee.changeBooking(first, Optional.of(DATE1.plusDays(2)), Optional.of(ACTIVITY2),
                    Optional.of(TIME3), Optional.of(TIME2), Optional.empty());
        } finally {
            assertFalse(mockStore.retrieveAll().isEmpty());
        }
    }
}
