/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine;

/**
 * An abstract base class with the central handleCommand method and some common resources needed by many handlers
 */
public abstract class AbstractCommandHandler {
	private static CommandLogger logger;
	private static ServiceManager services;

	public static void setLogger(final CommandLogger concreteLogger) {
		logger = concreteLogger;
	}

	protected static CommandLogger getLogger() {
		return logger;
	}

	public static void setServices(final ServiceManager concreteServices) {
		services = concreteServices;
	}

	protected static ServiceManager getServices() {
		return services;
	}

	public abstract void handleCommand();
}
