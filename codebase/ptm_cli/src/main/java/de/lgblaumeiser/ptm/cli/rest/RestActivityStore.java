/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.rest;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * Store that uses the rest utils to access the server, i.e., a proxy
 * implementation of Object Store
 */
public class RestActivityStore extends RestBaseService implements ObjectStore<Activity> {
	@Override
	public Collection<Activity> retrieveAll() {
		return asList(getRestUtils().<Activity[]>get("/activities", Activity[].class));
	}

	@Override
	public Optional<Activity> retrieveById(Long id) {
		return Optional.ofNullable(getRestUtils().<Activity>get("/activities/" + id.toString(), Activity.class));
	}

	@Override
	public Activity store(Activity activity) {
		try {
			Map<String, String> bodyData = newHashMap();
			bodyData.put("name", activity.getActivityName());
			bodyData.put("id", activity.getBookingNumber());
			Long id = getRestUtils().post("/activities", bodyData);
			Field idField = activity.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			idField.set(activity, id);
			idField.setAccessible(false);
			return activity;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void deleteById(Long id) {
		// Currently not supported operation for activities
	}
}
