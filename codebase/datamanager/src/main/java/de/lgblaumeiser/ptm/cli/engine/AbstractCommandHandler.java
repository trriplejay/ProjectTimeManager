/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine;

/**
 * An abstract implementation which manages common resources needed by many
 * handlers
 */
public abstract class AbstractCommandHandler implements CommandHandler {
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
}
