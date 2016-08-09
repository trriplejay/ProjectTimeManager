/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.json.mongo;

import org.bson.Document;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

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
	this.mongoDB = mongoDB;
    }

    @Override
    public void store(final String jsonObject) {
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
    public @Nullable String retrieveById(@NonNull final String id) {
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
    public @Nullable String retrieveByIndexKey(@NonNull final String key) {
	// TODO Auto-generated method stub
	return null;
    }

}
