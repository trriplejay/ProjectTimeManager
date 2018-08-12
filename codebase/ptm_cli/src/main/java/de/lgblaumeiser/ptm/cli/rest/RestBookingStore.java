/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.rest;

import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;

/**
 * Store that uses the rest utils to access the server, i.e., a proxy
 * implementation of Object Store
 */
public class RestBookingStore extends RestBaseService implements ObjectStore<Booking> {
	@Override
	public Collection<Booking> retrieveAll() {
		return retrieveForDay(LocalDate.now());
	}

	public Collection<Booking> retrieveForDay(LocalDate day) {
		return asList(getRestUtils().<Booking[]>get(
				"/bookings/day/" + day.format(DateTimeFormatter.ISO_LOCAL_DATE), Booking[].class));
	}

	@Override
	public Optional<Booking> retrieveById(Long id) {
		return Optional.ofNullable(getRestUtils().<Booking>get(
				"/bookings/id/" + id.toString(),
				Booking.class));
	}

	@Override
	public Booking store(Booking booking) {
		try {
			Map<String, String> bodyData = newHashMap();
			bodyData.put("activityId", booking.getActivity().getId().toString());
			bodyData.put("user", booking.getUser());
			bodyData.put("comment", booking.getComment());
			bodyData.put("starttime", booking.getStarttime().format(DateTimeFormatter.ofPattern("HH:mm")));
			if (booking.hasEndtime()) {
				bodyData.put("endtime", booking.getEndtime().format(DateTimeFormatter.ofPattern("HH:mm")));
			}
			String apiName = "/bookings/";
			if (booking.getId() > 0) {
				apiName = apiName + "id/" + booking.getId().toString();
			} else {
				apiName = apiName + "day/" + booking.getBookingday().format(DateTimeFormatter.ISO_LOCAL_DATE);
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
		getRestUtils().delete("/bookings/id/" + id);
	}
}
