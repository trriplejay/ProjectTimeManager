/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import static com.google.common.collect.Maps.newHashMap;
import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static de.lgblaumeiser.ptm.datamanager.model.Booking.newBooking;
import static de.lgblaumeiser.ptm.datamanager.model.DayBookings.newDay;
import static java.time.LocalDate.now;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.ptm.store.ObjectStore;

public abstract class AbstractComputerTest {

	protected static final LocalDate OTHERMONTH = LocalDate.of(2015, 12, 1);

	private static final String ACTIVITYNAME1 = "a";
	private static final String ACTIVITYNAME2 = "b";
	private static final String ACTIVITYNAME3 = "c";
	private static final String BOOKINGNUMBER1 = "d";
	private static final String BOOKINGNUMBER2 = "e";

	private static final Activity ACTIVITY1 = newActivity(ACTIVITYNAME1, BOOKINGNUMBER1);
	private static final Activity ACTIVITY2 = newActivity(ACTIVITYNAME2, BOOKINGNUMBER2);
	private static final Activity ACTIVITY3 = newActivity(ACTIVITYNAME3, BOOKINGNUMBER1);

	private static final LocalTime TIME1 = LocalTime.of(12, 34);
	private static final LocalTime TIME2 = LocalTime.of(13, 57);
	private static final LocalTime TIME3 = LocalTime.of(14, 35);
	private static final LocalTime TIME4 = LocalTime.of(8, 15);
	private static final LocalTime TIME5 = LocalTime.of(17, 25);
	private static final LocalTime TIME6 = LocalTime.of(9, 42);
	private static final LocalTime TIME7 = LocalTime.of(15, 39);
	private static final LocalTime TIME8 = LocalTime.of(18, 45);

	private static final Booking BOOKING1 = newBooking().setStarttime(TIME1).setEndtime(TIME2).setActivity(ACTIVITY1)
			.build();
	private static final Booking BOOKING2 = newBooking().setStarttime(TIME2).setEndtime(TIME3).setActivity(ACTIVITY2)
			.build();
	private static final Booking BOOKING3 = newBooking().setStarttime(TIME4).setEndtime(TIME6).setActivity(ACTIVITY3)
			.build();
	private static final Booking BOOKING4 = newBooking().setStarttime(TIME7).setEndtime(TIME5).setActivity(ACTIVITY1)
			.build();
	private static final Booking BOOKING5 = newBooking().setStarttime(TIME6).setEndtime(TIME3).setActivity(ACTIVITY2)
			.build();
	private static final Booking BOOKING6 = newBooking().setStarttime(TIME3).setEndtime(TIME5).setActivity(ACTIVITY3)
			.build();
	private static final Booking BOOKING7 = newBooking().setStarttime(TIME4).setEndtime(TIME7).setActivity(ACTIVITY1)
			.build();
	private static final Booking BOOKING8 = newBooking().setStarttime(TIME7).setEndtime(TIME8).setActivity(ACTIVITY2)
			.build();
	private static final Booking BOOKING9 = newBooking().setStarttime(TIME4).setActivity(ACTIVITY3).build();
	private static final Booking BOOKING10 = newBooking().setStarttime(TIME6).setEndtime(TIME8).setActivity(ACTIVITY1)
			.build();

	private static Map<LocalDate, DayBookings> testDataStore = newHashMap();

	static {
		LocalDate currentDay = LocalDate.of(now().getYear(), now().getMonthValue(), 1);

		DayBookings dayOne = newDay(currentDay);
		dayOne.addBooking(BOOKING1);
		dayOne.addBooking(BOOKING2);
		testDataStore.put(currentDay, dayOne);

		currentDay = currentDay.plusDays(4);
		DayBookings dayTwo = newDay(currentDay);
		dayTwo.addBooking(BOOKING3);
		dayTwo.addBooking(BOOKING4);
		testDataStore.put(currentDay, dayTwo);

		currentDay = currentDay.plusDays(4);
		DayBookings dayFour = newDay(currentDay);
		dayFour.addBooking(BOOKING9);
		testDataStore.put(currentDay, dayFour);

		currentDay = currentDay.plusDays(4);
		DayBookings dayThree = newDay(currentDay);
		dayThree.addBooking(BOOKING10);
		testDataStore.put(currentDay, dayThree);

		currentDay = OTHERMONTH;
		DayBookings dayFive = newDay(currentDay);
		dayFive.addBooking(BOOKING5);
		dayFive.addBooking(BOOKING6);
		testDataStore.put(currentDay, dayFive);

		currentDay = currentDay.plusDays(4);
		DayBookings daySix = newDay(currentDay);
		daySix.addBooking(BOOKING7);
		daySix.addBooking(BOOKING8);
		testDataStore.put(currentDay, daySix);

		currentDay = currentDay.plusDays(4);
		DayBookings daySeven = newDay(currentDay);
		daySeven.addBooking(BOOKING7);
		daySeven.addBooking(BOOKING8);
		testDataStore.put(currentDay, daySeven);
	}

	@Before
	public void before() {
		ObjectStore<DayBookings> testdata = new ObjectStore<DayBookings>() {
			@Override
			public DayBookings store(DayBookings object) {
				// Not needed for test
				return object;
			}

			@Override
			public Collection<DayBookings> retrieveAll() {
				return testDataStore.values();
			}

		};
		setTesteeStore(testdata);
	}

	protected abstract void setTesteeStore(ObjectStore<DayBookings> store);
}