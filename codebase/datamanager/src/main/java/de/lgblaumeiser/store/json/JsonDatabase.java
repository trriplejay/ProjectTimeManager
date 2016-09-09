/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.json;

/**
 * Interface that describes a database interface storing JSON objects resp.
 * retrieving objects to JSON
 */
public interface JsonDatabase {
    /**
     * Store json representation of object in the implementing data store
     *
     * @param jsonObject
     *            Json representation of the object to store
     */
    void store(String jsonObject);

    /**
     * Retrieve an json representation of an object by its id
     *
     * @param id
     *            The id as string
     * @return The json representation of the object stored with the id or null
     *         if the id is unknown
     */
    String retrieveById(String id);

    /**
     * Retrieve an json array of json objects by a key for which an index exists
     *
     * @param key
     *            The key as string
     * @return The json array of the objects found, might be null if no object
     *         is found.
     */
    String retrieveByIndexKey(Object key);
}
