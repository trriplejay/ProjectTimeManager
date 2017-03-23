/*
 * Copyright 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.rest;

import static java.lang.Long.parseLong;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * Rest Controller for Booking Management
 */
@RestController
@RequestMapping("/bookings")
public class BookingRestController {
	@Autowired
	private ServiceMapper services;

	@RequestMapping(method = RequestMethod.GET)
	public Map<LocalDate, Long> getDaysForWhichBookingsExist() {
		return emptyMap();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{dayId}")
	public Collection<Booking> getBookingsForDay(@PathVariable String dayId) {
		DayBookings requestedDay = services.bookingStore().retrieveById(parseLong(dayId));
		return requestedDay.getBookings();
	}

	static class BookingBody {
		public String activityId;
		public LocalTime starttime;
		public LocalTime endtime;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{dayId}")
	public ResponseEntity<?> addBooking(@PathVariable String dayId, @RequestBody BookingBody newData) {
		DayBookings requestedDay = services.bookingStore().retrieveById(parseLong(dayId));
		Activity activity = services.activityStore().retrieveById(parseLong(newData.activityId));
		Booking newBooking = services.bookingService().addBooking(requestedDay, activity, newData.starttime);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBooking.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{dayId}/{booking}")
	public Booking getBooking(@PathVariable String dayId, @PathVariable String booking) {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{dayId}/{booking}")
	public ResponseEntity<?> changeBooking(@PathVariable String dayId, @PathVariable String booking,
			@RequestBody BookingBody changedData) {
		return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{dayId}/{booking}")
	public ResponseEntity<?> deleteBooking(@PathVariable String dayId, @PathVariable String booking) {
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		return new ResponseEntity<String>(e.getMessage(), BAD_REQUEST);
	}
}
