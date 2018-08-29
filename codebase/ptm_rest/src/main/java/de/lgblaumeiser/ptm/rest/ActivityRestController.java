/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.rest;

import de.lgblaumeiser.ptm.datamanager.model.Activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ServiceMapper services;

	@RequestMapping(method = RequestMethod.GET,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Collection<Activity> getActivities() {
		logger.info("Request: Get all Activities");
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
		logger.info("Request: Post new Activity");
		Activity newActivity = services.activityStore().store(
				newActivity().setActivityName(activityData.activityName)
						.setBookingNumber(activityData.bookingNumber).setHidden(activityData.hidden).build());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newActivity.getId()).toUri();
		logger.info("Result: Activity Created with Id " + newActivity.getId());
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{activityId}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Activity getActivity(@PathVariable String activityId) {
		logger.info("Request: Get Activity with Id " + activityId);
		return services.activityStore().retrieveById(valueOf(activityId)).orElseThrow(IllegalStateException::new);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{activityId}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<?> changeActivity(@PathVariable String activityId, @RequestBody ActivityBody activityData) {
		logger.info("Request: Post changed Activity, id Id for change: " + activityId);
		services.activityStore().retrieveById(valueOf(activityId)).ifPresent(a ->
			services.activityStore().store(
					a.changeActivity().setActivityName(activityData.activityName)
							.setBookingNumber(activityData.bookingNumber)
							.setHidden(activityData.hidden).build())
		);
		logger.info("Result: Activity changed");
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		logger.error("Exception in Request", e);
		return ResponseEntity.status(BAD_REQUEST).body(e.toString());
	}
}
