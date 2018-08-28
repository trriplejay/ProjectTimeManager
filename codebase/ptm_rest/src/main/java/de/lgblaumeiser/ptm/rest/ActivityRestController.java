/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.rest;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static java.lang.Long.valueOf;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Rest Controller for management of activities
 */
@RestController
@RequestMapping("/activities")
public class ActivityRestController {
	@Autowired
	private ServiceMapper services;

	@RequestMapping(method = RequestMethod.GET,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Collection<Activity> getActivities() {
		return services.activityStore().retrieveAll();
	}

	static class ActivityBody {
		public String activityName;
		public String bookingNumber;
		public boolean hidden;
	}

	@RequestMapping(method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<?> addActivity(@RequestBody ActivityBody activityData) {
		Activity newActivity = services.activityStore().store(
				newActivity().setActivityName(activityData.activityName)
						.setBookingNumber(activityData.bookingNumber).setHidden(activityData.hidden).build());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newActivity.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{activityId}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Activity getActivity(@PathVariable String activityId) {
		return services.activityStore().retrieveById(valueOf(activityId)).orElseThrow(IllegalStateException::new);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{activityId}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<?> changeActivity(@PathVariable String activityId, @RequestBody ActivityBody activityData) {
		services.activityStore().retrieveById(valueOf(activityId)).ifPresent(a ->
			services.activityStore().store(
					a.changeActivity().setActivityName(activityData.activityName)
							.setBookingNumber(activityData.bookingNumber)
							.setHidden(activityData.hidden).build())
		);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
	}
}
