/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.json;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.Gson;

import de.lgblaumeiser.store.ObjectStore;

/**
 * Class to experiment with Mongo DB as backend db
 */
public class JsonStore<T> implements ObjectStore<T> {
    private JsonDatabase backend;
    private final Gson gsonUtil = new Gson();

    public void setBackend(final JsonDatabase backend) {
	this.backend = backend;
    }

    @Override
    public void store(@NonNull final T object) {
	String json = gsonUtil.toJson(object);
	backend.store(json);
    }

    @Override
    @Nullable
    public T retrieveById(@NonNull final String id, @NonNull final Class<T> resultClass) {
	String json = backend.retrieveById(id);
	if (StringUtils.isBlank(json)) {
	    return null;
	}
	return gsonUtil.<T>fromJson(json, resultClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public Collection<T> retrieveByIndexKey(@NonNull final String key, @NonNull final Class<T> resultClass) {
	String json = backend.retrieveByIndexKey(key);
	Collection<T> back = new ArrayList<T>();
	if (StringUtils.isNotBlank(json)) {
	    back.addAll(gsonUtil.fromJson(json, Collection.class));
	}
	return back;
    }

}
