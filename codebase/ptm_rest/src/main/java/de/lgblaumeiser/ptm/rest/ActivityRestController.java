/*
 * Copyright 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.rest;

import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static java.lang.Long.parseLong;
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

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> addActivity(@RequestBody String name, @RequestBody String id) {
		Activity newActivity = services.activityStore().store(newActivity(name, id));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newActivity.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{activityId}")
	Activity getActivity(@PathVariable String activityId) {
		long id = parseLong(activityId);
		return services.activityStore().retrieveAll().stream().filter(a -> a.getId().longValue() == id).findFirst()
				.orElseThrow(() -> new IllegalStateException("Not Found"));
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		return new ResponseEntity<String>(e.getMessage(), BAD_REQUEST);
	}
}
