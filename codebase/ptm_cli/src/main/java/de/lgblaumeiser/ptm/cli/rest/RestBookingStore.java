/*
 * Copyright 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.rest;

import static com.google.common.collect.Maps.newHashMap;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import static java.util.Arrays.asList;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * Store that uses the rest utils to access the server, i.e., a proxy
 * implementation of Object Store
 */
public class RestBookingStore extends RestBaseService implements ObjectStore<Booking> {
	@Override
	public Collection<Booking> retrieveAll() {
		return asList(getRestUtils().<Booking[]>get(
				"/bookings" + "/" + getServices().getStateStore().getCurrentDayString(), Booking[].class));
	}

	@Override
	public Optional<Booking> retrieveById(Long id) {
		return Optional.ofNullable(getRestUtils().<Booking>get(
				"/bookings/" + getServices().getStateStore().getCurrentDayString() + "/" + id.toString(),
				Booking.class));
	}

	@Override
	public Booking store(Booking booking) {
		try {
			Map<String, String> bodyData = newHashMap();
			bodyData.put("activityId", booking.getActivity().getId().toString());
			bodyData.put("starttime", booking.getStarttime().format(ISO_LOCAL_TIME));
			if (booking.hasEndtime()) {
				bodyData.put("endtime", booking.getEndtime().format(ISO_LOCAL_TIME));
			}
			String apiName = "/bookings/" + booking.getBookingday().format(ISO_LOCAL_DATE);
			if (booking.getId() > 0) {
				apiName = apiName + "/" + booking.getId().toString();
			}
			Long id = getRestUtils().post(apiName, bodyData);
			if (booking.getId() < 0) {
				Field idField = booking.getClass().getDeclaredField("id");
				idField.setAccessible(true);
				idField.set(booking, id);
				idField.setAccessible(false);
			}
			return booking;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void deleteById(Long id) {
		getRestUtils().delete("/bookings/" + getServices().getStateStore().getCurrentDayString() + "/" + id);
	}
}
