/*
 * Copyright 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.rest;

import static java.lang.Long.valueOf;
import static java.time.LocalTime.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.net.URI;
import java.time.LocalDate;
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
	public Collection<String> getDaysForWhichBookingsExist() {
		return services.bookingStore().retrieveAll().stream().map(b -> b.getBookingday()).distinct()
				.map(d -> d.format(ISO_LOCAL_DATE)).sorted().collect(toList());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{dayString}")
	public Collection<Booking> getBookingsForDay(@PathVariable String dayString) {
		LocalDate day = LocalDate.parse(dayString);
		return services.bookingStore().retrieveAll().stream().filter(b -> b.getBookingday().equals(day))
				.sorted((b1, b2) -> b1.getBookingday().compareTo(b2.getBookingday())).collect(toList());
	}

	static class BookingBody {
		public String activityId;
		public String starttime;
		public String endtime;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{dayString}")
	public ResponseEntity<?> addBooking(@PathVariable String dayString, @RequestBody BookingBody newData) {
		LocalDate day = LocalDate.parse(dayString);
		Activity activity = services.activityStore().retrieveById(valueOf(newData.activityId))
				.orElseThrow(IllegalStateException::new);
		Booking newBooking = services.bookingService().addBooking(day, activity, parse(newData.starttime));
		if (newData.endtime != null) {
			newBooking = services.bookingService().endBooking(newBooking, parse(newData.endtime));
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{day}/{id}")
				.buildAndExpand(day.format(ISO_LOCAL_DATE), newBooking.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{dayString}/{booking}")
	public Booking getBooking(@PathVariable String dayString, @PathVariable String booking) {
		return services.bookingStore().retrieveById(valueOf(booking)).orElseThrow(IllegalStateException::new);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{dayString}/{booking}")
	public ResponseEntity<?> endBooking(@PathVariable String dayString, @PathVariable String booking,
			@RequestBody BookingBody changedData) {
		services.bookingStore().retrieveById(valueOf(booking))
				.ifPresent(b -> services.bookingService().endBooking(b, parse(changedData.endtime)));
		return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{dayString}/{booking}")
	public ResponseEntity<?> deleteBooking(@PathVariable String dayString, @PathVariable String booking) {
		services.bookingStore().deleteById(valueOf(booking));
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		return new ResponseEntity<String>(e.getMessage(), BAD_REQUEST);
	}
}
