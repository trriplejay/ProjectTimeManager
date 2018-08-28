/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;
import org.junit.Before;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static de.lgblaumeiser.ptm.datamanager.model.Booking.newBooking;

public abstract class AbstractComputerTest {

	protected static final LocalDate OTHERMONTH = LocalDate.of(2015, 12, 1);

	private static final String ACTIVITYNAME1 = "a";
	private static final String ACTIVITYNAME2 = "b";
	private static final String ACTIVITYNAME3 = "c";
	private static final String BOOKINGNUMBER1 = "d";
	private static final String BOOKINGNUMBER2 = "e";

	private static final Activity ACTIVITY1 = newActivity().setActivityName(ACTIVITYNAME1).setBookingNumber(BOOKINGNUMBER1).build();
	private static final Activity ACTIVITY2 = newActivity().setActivityName(ACTIVITYNAME2).setBookingNumber(BOOKINGNUMBER2).build();
	private static final Activity ACTIVITY3 = newActivity().setActivityName(ACTIVITYNAME3).setBookingNumber(BOOKINGNUMBER1).build();

	private static final LocalTime TIME1 = LocalTime.of(12, 34);
	private static final LocalTime TIME2 = LocalTime.of(13, 57);
	private static final LocalTime TIME3 = LocalTime.of(14, 35);
	private static final LocalTime TIME4 = LocalTime.of(8, 15);
	private static final LocalTime TIME5 = LocalTime.of(17, 25);
	private static final LocalTime TIME6 = LocalTime.of(9, 42);
	private static final LocalTime TIME7 = LocalTime.of(15, 39);
	private static final LocalTime TIME8 = LocalTime.of(18, 45);

	private static final String USER = "TestUser";

	private static final Booking BOOKING1 = newBooking().setBookingday(LocalDate.of(2017, 3, 1)).setStarttime(TIME1)
			.setEndtime(TIME2).setActivity(ACTIVITY1).setUser(USER).build();
	private static final Booking BOOKING2 = newBooking().setBookingday(LocalDate.of(2017, 3, 1)).setStarttime(TIME2)
			.setEndtime(TIME3).setActivity(ACTIVITY2).setUser(USER).build();
	private static final Booking BOOKING3 = newBooking().setBookingday(LocalDate.of(2017, 3, 6)).setStarttime(TIME4)
			.setEndtime(TIME6).setActivity(ACTIVITY3).setUser(USER).build();
	private static final Booking BOOKING4 = newBooking().setBookingday(LocalDate.of(2017, 3, 6)).setStarttime(TIME7)
			.setEndtime(TIME5).setActivity(ACTIVITY1).setUser(USER).build();
	private static final Booking BOOKING5 = newBooking().setBookingday(LocalDate.of(2017, 3, 9)).setStarttime(TIME6)
			.setEndtime(TIME3).setActivity(ACTIVITY2).setUser(USER).build();
	private static final Booking BOOKING6 = newBooking().setBookingday(LocalDate.of(2017, 3, 9)).setStarttime(TIME3)
			.setEndtime(TIME5).setActivity(ACTIVITY3).setUser(USER).build();
	private static final Booking BOOKING7 = newBooking().setBookingday(LocalDate.of(2017, 3, 15)).setStarttime(TIME4)
			.setEndtime(TIME7).setActivity(ACTIVITY1).setUser(USER).build();
	private static final Booking BOOKING8 = newBooking().setBookingday(LocalDate.of(2017, 3, 15)).setStarttime(TIME7)
			.setEndtime(TIME8).setActivity(ACTIVITY2).setUser(USER).build();
	private static final Booking BOOKING9 = newBooking().setBookingday(LocalDate.of(2017, 3, 24)).setStarttime(TIME4)
			.setActivity(ACTIVITY3).setUser(USER).build();
	private static final Booking BOOKING10 = newBooking().setBookingday(LocalDate.of(2017, 3, 28)).setStarttime(TIME6)
			.setEndtime(TIME8).setActivity(ACTIVITY1).setUser(USER).build();

	private static Collection<Booking> testDataStore = newArrayList(BOOKING1, BOOKING2, BOOKING3, BOOKING4, BOOKING5,
			BOOKING6, BOOKING7, BOOKING8, BOOKING9, BOOKING10);

	@Before
	public void before() {
		ObjectStore<Booking> testdata = new ObjectStore<Booking>() {
			@Override
			public Booking store(Booking object) {
				// Not needed for test
				return object;
			}

			@Override
			public Collection<Booking> retrieveAll() {
				return testDataStore;
			}

			@Override
			public Optional<Booking> retrieveById(Long id) {
				// Not needed here
				return Optional.empty();
			}

			@Override
			public void deleteById(Long id) {
				// Not needed here
			}
		};
		createTestee(testdata);
	}

	protected abstract void createTestee(ObjectStore<Booking> store);
}