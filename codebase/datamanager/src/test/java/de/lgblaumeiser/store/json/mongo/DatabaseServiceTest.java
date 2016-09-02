/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.json.mongo;

import java.time.LocalDate;
import java.time.LocalTime;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.store.json.JsonStore;

public class DatabaseServiceTest {
    @SuppressWarnings("null")
    @NonNull
    private static final LocalDate DATE1 = LocalDate.of(2016, 06, 24);

    @SuppressWarnings("null")
    @NonNull
    private static final LocalTime TIME1 = LocalTime.of(12, 34);

    @SuppressWarnings("null")
    @NonNull
    private static final LocalTime TIME2 = LocalTime.of(13, 57);

    @SuppressWarnings("null")
    @NonNull
    private static final LocalTime TIME3 = LocalTime.of(14, 35);

    @NonNull
    private static final Activity ACTIVITY1 = Activity.newLineActivity("Act1", "0815");

    @NonNull
    private static final Booking BOOKING1 = Booking.newBooking().setStarttime(TIME1).setEndtime(TIME2)
	    .setActivity(ACTIVITY1).build();

    @NonNull
    private static final Booking BOOKING2 = Booking.newBooking().setStarttime(TIME2).setEndtime(TIME3)
	    .setActivity(ACTIVITY1).build();

    @NonNull
    private final DayBookings testdata = DayBookings.newDay(DATE1);

    private final JsonStore<DayBookings> testee = new JsonStore<>();

    @Before
    public void setUp() throws Exception {
	testee.setBackend(new DatabaseService());
    }

    @Test
    public void test() {
	// testee.store(testdata);
    }

}
