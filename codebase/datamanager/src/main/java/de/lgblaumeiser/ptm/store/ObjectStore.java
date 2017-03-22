/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.store;

import java.util.Collection;

/**
 * A general interface for storing objects
 */
public interface ObjectStore<T> {
	/**
	 * Store an object in the store
	 *
	 * @param object
	 *            The Object to store
	 * @return The stored object
	 */
	T store(T object);

	/**
	 * Retrieve all objects stored in the store
	 * 
	 * @return All objects found in the store
	 * @return
	 */
	Collection<T> retrieveAll();
}
