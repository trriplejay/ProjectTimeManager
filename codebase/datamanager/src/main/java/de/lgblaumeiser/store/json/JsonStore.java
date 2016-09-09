/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.json;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

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
    public void store(final T object) {
	checkNotNull(object);
	String json = gsonUtil.toJson(object);
	backend.store(json);
    }

    @Override
    public T retrieveByIndexKey(final Object key) {
	checkNotNull(key);
	String json = backend.retrieveByIndexKey(key);
	if (StringUtils.isNotBlank(json)) {
	    return gsonUtil.fromJson(json, getDataClass());
	}
	return null;
    }

    @Override
    public void configure(final Properties properties) {
	checkNotNull(properties);
	storageProperties = properties;
    }

    @Override
    protected boolean allPropertiesSet() {
	return !storageProperties.isEmpty();
    }

}
