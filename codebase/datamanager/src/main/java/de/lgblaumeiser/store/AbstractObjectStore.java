/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * A base implementation which includes the management of Properties
 */
public abstract class AbstractObjectStore<T> implements ObjectStore<T> {
	private Properties storageProperties;
	private Class<T> storageClass;

	@SuppressWarnings("unchecked")
	@Override
	public void configure(final Properties properties) {
		checkNotNull(properties);
		storageProperties = new Properties(properties);
		String classKey = properties.getProperty(CLASS_KEY);
		checkState(isNotBlank(classKey));
		try {
			storageClass = (Class<T>) Class.forName(classKey);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
		checkState(allPropertiesSet());
	}

	protected abstract boolean allPropertiesSet();

	private String getIndexGetter() {
		checkState(storageProperties != null);
		String index = storageProperties.getProperty(INDEX_KEY);
		return "get" + index.substring(0, 1).toUpperCase() + index.substring(1);
	}

	protected Class<T> getDataClass() {
		checkState(storageClass != null);
		return storageClass;
	}

	protected String getPropertyWithKey(final String key) {
		checkState(storageProperties != null);
		return storageProperties.getProperty(key);
	}

	protected Object getIndexObject(final T object) {
		checkNotNull(object);
		String searchedMethod = getIndexGetter();
		for (Method m : object.getClass().getDeclaredMethods()) {
			if (searchedMethod.equals(m.getName())) {
				try {
					m.setAccessible(true);
					Object returnVal = m.invoke(object);
					m.setAccessible(false);
					return returnVal;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new IllegalStateException(e);
				}
			}
		}
		throw new IllegalStateException("Index method not found");
	}
}
