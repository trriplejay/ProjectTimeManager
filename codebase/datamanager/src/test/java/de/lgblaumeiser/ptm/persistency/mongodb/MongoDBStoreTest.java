package de.lgblaumeiser.ptm.persistency.mongodb;

import java.time.LocalDate;
import java.time.LocalTime;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

public class MongoDBStoreTest {
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

    private final DayBookings testee = DayBookings.newDay(DATE1);

    @Test
    public void test() {
	testee.addBooking(BOOKING1);
	testee.addBooking(BOOKING2);
	MongoDBStore store = new MongoDBStore();
	store.rundbtest(testee);
    }
}
