/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;

import com.google.gson.Gson;

import de.lgblaumeiser.store.ObjectStore;

/**
 * Class to experiment with Mongo DB as backend db
 */
public class JsonStore<T> implements ObjectStore<T> {
    private JsonDatabase backend;
    private final Gson gsonUtil = new Gson();
    private Properties storageProperties;

    public void setBackend(final JsonDatabase backend) {
	this.backend = backend;
    }

    @Override
    public void store(@NonNull final T object) {
	String json = gsonUtil.toJson(object);
	backend.store(json);
    }

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public Collection<T> retrieveByIndexKey(@NonNull final Object key) {
	String json = backend.retrieveByIndexKey(key);
	Collection<T> back = new ArrayList<T>();
	if (StringUtils.isNotBlank(json)) {
	    back.addAll(gsonUtil.fromJson(json, Collection.class));
	}
	return back;
    }

    @Override
    public void configure(@NonNull final Properties properties) {
	storageProperties = properties;
    }

}
