/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.json;

import java.time.LocalDate;
import java.time.LocalTime;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.Before;
import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

public class JsonStoreTest {
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

    @NonNull
    private final JsonStore<DayBookings> testee = new JsonStore<>();

    @Before
    public void setup() {
	testdata.addBooking(BOOKING1);
	testdata.addBooking(BOOKING2);
	testee.setBackend(new JsonDatabase() {
	    String jsonObject;

	    @Override
	    public void store(final String jsonObject) {
		this.jsonObject = jsonObject;

	    }

	    @Override
	    public @Nullable String retrieveById(@NonNull final String id) {
		return jsonObject;
	    }

	    @Override
	    public @Nullable String retrieveByIndexKey(@NonNull final Object key) {
		return jsonObject;
	    }

	});
    }

    @SuppressWarnings("null")
    @Test
    public void test() {
	testee.store(testdata);
	// assertEquals(testdata,
	// testee.retrieveById(testdata.getDay().toString(),
	// DayBookings.class));
	// MongoClient mongoClient = new MongoClient();
	// MongoDatabase db = mongoClient.getDatabase("ProjectTimeManager");

	// String json = gsonUtil.toJson(obj);
	// Document doc = Document.parse(json);
	// // db.getCollection("NameColl").insertOne(doc);
	// System.out.println(doc);
	// // mongoClient.close();
	// String jsonret = doc.toJson();
	// T ret = gsonUtil.<T>fromJson(jsonret, obj.getClass());
	// return ret;

    }
}
