/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.rest;

import de.lgblaumeiser.ptm.cli.engine.ServiceManager;

/**
 * Base functionality used by all
 */
public class RestBaseService {
	private static ServiceManager services;
	private static RestUtils utils;

	public static RestUtils getRestUtils() {
		return utils;
	}

	public static void setRestUtils(RestUtils restUtils) {
		utils = restUtils;
	}

	public static void setServices(final ServiceManager concreteServices) {
		services = concreteServices;
	}

	protected static ServiceManager getServices() {
		return services;
	}
}
