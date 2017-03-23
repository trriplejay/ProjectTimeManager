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
	 * Retrieve all objects stored in the store
	 * 
	 * @return All objects found in the store
	 */
	Collection<T> retrieveAll();

	/**
	 * Retrieve object by the id
	 * 
	 * @param id
	 *            Id of the searched object
	 * @return The object with the given id
	 * @throws IllegalStateException
	 *             If the object of the id is not found
	 */
	T retrieveById(Long id);

	/**
	 * Store an object in the store
	 *
	 * @param object
	 *            The Object to store
	 * @return The stored object
	 */
	T store(T object);

	/**
	 * Delete an object identified by its id
	 * 
	 * @param id
	 *            The id of the object to be deleted
	 */
	void deleteById(Long id);
}
