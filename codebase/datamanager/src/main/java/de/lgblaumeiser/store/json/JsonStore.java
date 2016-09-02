/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.json;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;

import com.google.gson.Gson;

import de.lgblaumeiser.store.AbstractObjectStore;

/**
 * Class to experiment with Mongo DB as backend db
 */
public class JsonStore<T> extends AbstractObjectStore<T> {
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

    @SuppressWarnings("null")
    @Override
    @NonNull
    public T retrieveByIndexKey(@NonNull final Object key) {
	String json = backend.retrieveByIndexKey(key);
	if (StringUtils.isNotBlank(json)) {
	    return gsonUtil.fromJson(json, getDataClass());
	}
	return null;
    }

    @Override
    public void configure(@NonNull final Properties properties) {
	storageProperties = properties;
    }

    @Override
    protected boolean allPropertiesSet() {
	return true;
    }

}
