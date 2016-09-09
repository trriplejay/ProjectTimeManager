/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.json.mongo;

import static com.google.common.base.Preconditions.checkNotNull;

import org.bson.Document;

import de.lgblaumeiser.store.json.JsonDatabase;

/**
 * Implementation of JsonDatabase interface to prepare data for storage in mongo
 * db
 *
 * MongoDb driver needs to be added
 */
public class DatabaseService implements JsonDatabase {
    private MongoDriver mongoDB;

    /**
     * Injector for mongo db driver
     *
     * @param mongoDB
     */
    public void setMongoDB(final MongoDriver mongoDB) {
	checkNotNull(mongoDB);
	this.mongoDB = mongoDB;
    }

    @Override
    public void store(final String jsonObject) {
	checkNotNull(jsonObject);
	Document doc = Document.parse(jsonObject);
	if (mongoDB.contains(doc.get("day"))) {
	    mongoDB.update(doc);
	} else {
	    mongoDB.insert(doc);
	}
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.lgblaumeiser.store.json.JsonDatabase#retrieveById(java.lang.String)
     */
    @Override
    public String retrieveById(final String id) {
	checkNotNull(id);
	// TODO Auto-generated method stub
	return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.lgblaumeiser.store.json.JsonDatabase#retrieveByIndexKey(java.lang.
     * String)
     */
    @Override
    public String retrieveByIndexKey(final Object key) {
	checkNotNull(key);
	// TODO Auto-generated method stub
	return null;
    }

}
