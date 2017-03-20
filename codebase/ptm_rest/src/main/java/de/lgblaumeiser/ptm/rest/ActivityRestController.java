/*
 * Copyright 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.rest;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.service.ActivityService;

/**
 * Rest Controller for management of activities
 */
@RestController
@RequestMapping("/activities")
public class ActivityRestController {

	@Autowired
	private ActivityService activityService;

	@RequestMapping(method = RequestMethod.GET)
	Collection<Activity> getActivities() {
		return activityService.getActivityModel().getActivities();
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> addActivity(@RequestBody String name, @RequestBody String id) {
		Activity newActivity = activityService.addActivity(name, id);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newActivity.getActivityName()).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{activityId}")
	Activity getActivity(@PathVariable String activityId) {
		return activityService.getActivityByAbbreviatedName(activityId);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
