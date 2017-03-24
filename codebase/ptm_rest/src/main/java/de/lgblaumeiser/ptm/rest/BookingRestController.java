/*
 * Copyright 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.rest;

import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

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

/**
 * Rest Controller for Booking Management
 */
@RestController
@RequestMapping("/bookings")
public class BookingRestController {
	@Autowired
	private ServiceMapper services;

	@RequestMapping(method = RequestMethod.GET)
	public Collection<LocalDate> getDaysForWhichBookingsExist() {
		return services.bookingStore().retrieveAll().stream().map(b -> b.getBookingday()).distinct().sorted()
				.collect(toList());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{day}")
	public Collection<Booking> getBookingsForDay(@PathVariable LocalDate day) {
		return services.bookingStore().retrieveAll().stream().filter(b -> b.getBookingday().equals(day)).sorted()
				.collect(toList());
	}

	static class BookingBody {
		public String activityId;
		public LocalTime starttime;
		public LocalTime endtime;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{day}")
	public ResponseEntity<?> addBooking(@PathVariable LocalDate day, @RequestBody BookingBody newData) {
		Activity activity = services.activityStore().retrieveById(parseLong(newData.activityId))
				.orElseThrow(IllegalStateException::new);
		Booking newBooking = services.bookingService().addBooking(day, activity, newData.starttime);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBooking.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{day}/{booking}")
	public Booking getBooking(@PathVariable LocalDate day, @PathVariable String booking) {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{day}/{booking}")
	public ResponseEntity<?> changeBooking(@PathVariable String day, @PathVariable String booking,
			@RequestBody BookingBody changedData) {
		return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{day}/{booking}")
	public ResponseEntity<?> deleteBooking(@PathVariable String day, @PathVariable String booking) {
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		return new ResponseEntity<String>(e.getMessage(), BAD_REQUEST);
	}
}
