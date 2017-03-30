/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine;

import de.lgblaumeiser.ptm.cli.rest.RestUtils;

/**
 * Small class that allows to access the services needed by the command handler
 * implementations.
 */
public class ServiceManager {
	private RestUtils restUtils;
	private final StateStore stateStore = new StateStore();

	public RestUtils getRestUtils() {
		return restUtils;
	}

	public void setRestUtils(RestUtils restUtils) {
		this.restUtils = restUtils;
	}

	public StateStore getStateStore() {
		return stateStore;
	}
}
