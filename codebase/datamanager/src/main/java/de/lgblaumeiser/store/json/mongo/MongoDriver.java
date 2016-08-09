/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.json.mongo;

import org.bson.Document;
import org.eclipse.jdt.annotation.NonNull;

/**
 * Driver to directly access MongoDB
 */
class MongoDriver {
    void test() {
	// testee.store(testdata);
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

    boolean contains(@NonNull final Object localDate) {
	// TODO Auto-generated method stub
	return false;
    }

    void update(final Document doc) {
	// TODO Auto-generated method stub

    }

    void insert(final Document doc) {
	// TODO Auto-generated method stub

    }

}
