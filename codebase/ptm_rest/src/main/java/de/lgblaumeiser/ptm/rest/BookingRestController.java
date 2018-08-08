/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.rest;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;

import static java.lang.Long.valueOf;
import static java.time.LocalTime.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

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

	@RequestMapping(method = RequestMethod.GET, value = "/day/{dayString}")
	public Collection<Booking> getBookingsForDay(@PathVariable String dayString) {
		LocalDate day = LocalDate.parse(dayString);
		return services.bookingStore().retrieveAll().stream().filter(b -> b.getBookingday().equals(day))
				.sorted(Comparator.comparing(Booking::getStarttime)).collect(toList());
	}

	static class BookingBody {
		public String activityId;
		public String user;
		public String starttime;
		public String endtime;
		public String comment;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/day/{dayString}")
	public ResponseEntity<?> addBooking(@PathVariable String dayString, @RequestBody BookingBody newData) {
		LocalDate day = LocalDate.parse(dayString);
		Activity activity = services.activityStore().retrieveById(valueOf(newData.activityId))
				.orElseThrow(IllegalStateException::new);
		Booking newBooking = services.bookingService().addBooking(day, newData.user, activity, parse(newData.starttime), newData.comment);
		if (newData.endtime != null) {
			newBooking = services.bookingService().endBooking(newBooking, parse(newData.endtime));
		}
		URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/bookings/id/" + newBooking.getId());
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/id/{booking}")
	public Booking getBooking(@PathVariable String booking) {
		return services.bookingStore().retrieveById(valueOf(booking)).orElseThrow(IllegalStateException::new);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/id/{booking}")
	public ResponseEntity<?> endBooking(@PathVariable String booking,
			@RequestBody BookingBody changedData) {
		services.bookingStore().retrieveById(valueOf(booking))
				.ifPresent(b -> services.bookingService().endBooking(b, parse(changedData.endtime)));
		return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/id/{booking}")
	public ResponseEntity<?> deleteBooking(@PathVariable String booking) {
		services.bookingStore().deleteById(valueOf(booking));
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		return new ResponseEntity<String>(e.getMessage(), BAD_REQUEST);
	}
}
