/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store;

import java.util.Properties;

/**
 * A general interface for storing objects
 */
public interface ObjectStore<T> {
    static final String CLASS_KEY = "class";
    static final String INDEX_KEY = "index";

    /**
     * Set the properties for the persistence mechanism. The keys depend on the
     * storage technology used in the background. One key is defined as "index"
     * and contains the name of the field in the stored object that is used as
     * index key. Another key is defined by the class of T named "class";
     *
     * @param properties
     *            The properties of the persistence mechanism
     */
    void configure(Properties properties);

    /**
     * Store an object in the store
     *
     * @param object
     *            The Object to store
     */
    void store(T object);

    /**
     * Retrieve objects by a key for which an index exists
     *
     * @param key
     *            The key for which the data is searched
     * @return The object found, might be null if no object is found with key
     */
    T retrieveByIndexKey(Object key);
}
