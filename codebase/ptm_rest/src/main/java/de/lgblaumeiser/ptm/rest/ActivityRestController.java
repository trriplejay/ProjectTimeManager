/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.rest;

import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static java.lang.Long.valueOf;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.net.URI;
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

/**
 * Rest Controller for management of activities
 */
@RestController
@RequestMapping("/activities")
public class ActivityRestController {
	@Autowired
	private ServiceMapper services;

	@RequestMapping(method = RequestMethod.GET)
	Collection<Activity> getActivities() {
		return services.activityStore().retrieveAll();
	}

	static class ActivityBody {
		public String name;
		public String id;
		public boolean hidden;
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> addActivity(@RequestBody ActivityBody activityData) {
		Activity newActivity = services.activityStore().store(
				newActivity().setActivityName(activityData.name)
						.setBookingNumber(activityData.id).setHidden(activityData.hidden).build());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newActivity.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{activityId}")
	Activity getActivity(@PathVariable String activityId) {
		return services.activityStore().retrieveById(valueOf(activityId)).orElseThrow(IllegalStateException::new);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{activityId}")
	ResponseEntity<?> deleteActivity(@PathVariable String activityId) {
		services.activityStore().deleteById(valueOf(activityId));
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		return new ResponseEntity<String>(e.getMessage(), BAD_REQUEST);
	}
}
