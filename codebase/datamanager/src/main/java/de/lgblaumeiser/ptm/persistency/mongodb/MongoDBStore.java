/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.persistency.mongodb;

import org.bson.Document;

import com.google.gson.Gson;

import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * Class to experiment with Mongo DB as backend db
 */
public class MongoDBStore {
    public void rundbtest(final DayBookings bookings) {
	// MongoClient mongoClient = new MongoClient();
	// MongoDatabase db = mongoClient.getDatabase("ProjectTimeManager");

	Gson gson = new Gson();
	String json = gson.toJson(bookings);
	Document doc = Document.parse(json);
	// db.getCollection("NameColl").insertOne(doc);
	System.out.println(doc);
	// mongoClient.close();
    }

}
