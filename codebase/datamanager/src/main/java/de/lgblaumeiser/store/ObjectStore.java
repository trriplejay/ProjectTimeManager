/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store;

import java.util.Collection;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A general interface for storing objects
 */
public interface ObjectStore<T> {
    /**
     * Store an object in the store
     *
     * @param object
     *            The Object to store
     */
    void store(@NonNull T object);

    /**
     * Retrieve an object by its id
     *
     * @param id
     *            The id as string
     * @param resultClass
     *            Class of the return type
     * @return The object stored with the id or null if the id is unknown
     */
    @Nullable
    T retrieveById(@NonNull String id, @NonNull Class<T> resultClass);

    /**
     * Retrieve objects by a key for which an index exists
     *
     * @param key
     *            The key as string
     * @param resultClass
     *            Class of the return type
     * @return The objects found, might be empty
     */
    @NonNull
    Collection<T> retrieveByIndexKey(@NonNull String key, @NonNull Class<T> resultClass);
}
