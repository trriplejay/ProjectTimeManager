/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli;

import static de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler.setLogger;
import static de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler.setServices;
import static java.lang.System.getProperty;
import static java.lang.System.setProperty;

import java.io.File;

import de.lgblaumeiser.ptm.cli.engine.CommandInterpreter;
import de.lgblaumeiser.ptm.cli.engine.CommandLogger;
import de.lgblaumeiser.ptm.cli.engine.ServiceManager;
import de.lgblaumeiser.ptm.cli.engine.handler.AddActivity;
import de.lgblaumeiser.ptm.cli.engine.handler.AddBooking;
import de.lgblaumeiser.ptm.cli.engine.handler.DeleteBooking;
import de.lgblaumeiser.ptm.cli.engine.handler.EndBooking;
import de.lgblaumeiser.ptm.cli.engine.handler.ListActivity;
import de.lgblaumeiser.ptm.cli.engine.handler.ListBookings;
import de.lgblaumeiser.ptm.cli.engine.handler.OpenDay;
import de.lgblaumeiser.ptm.cli.engine.handler.RunAnalysis;
import de.lgblaumeiser.ptm.cli.rest.RestUtils;

/**
 * The configuration object that creates the application structure
 */
public class PTMCLIConfigurator {
	private static final String ADD_ACTIVITY_COMMAND = "AA";
	private static final String LIST_ACTIVITY_COMMAND = "LA";
	private static final String OPEN_DAY_COMMAND = "OD";
	private static final String ADD_BOOKING_COMMAND = "AB";
	private static final String DELETE_BOOKING_COMMAND = "DB";
	private static final String END_BOOKING_COMMAND = "EB";
	private static final String LIST_BOOKING_COMMAND = "LB";
	private static final String RUN_ANALYIS_COMMAND = "RA";
	// private static final String ANALYSIS_HOURS_ID = "HOURS";
	// private static final String ANALYSIS_PROJECTS_ID = "PROJECTS";

	public CLI configure() {
		setProperty("filestore.folder", new File(getProperty("user.home"), ".ptm").getAbsolutePath());
		CommandInterpreter interpreter = createCommandInterpreter();
		return createCLI(interpreter);
	}

	// private DataAnalysisService createAnalysisService(final
	// ObjectStore<Booking> store) {
	// DataAnalysisServiceImpl service = new DataAnalysisServiceImpl();
	// HourComputer hourComputer = new HourComputer();
	// hourComputer.setStore(store);
	// service.addAnalysis(ANALYSIS_HOURS_ID, hourComputer);
	// ProjectComputer projectComputer = new ProjectComputer();
	// projectComputer.setStore(store);
	// service.addAnalysis(ANALYSIS_PROJECTS_ID, projectComputer);
	// return service;
	// }
	//
	private CommandInterpreter createCommandInterpreter() {
		CommandInterpreter interpreter = new CommandInterpreter();
		CommandLogger logger = new StdoutLogger();
		ServiceManager serviceManager = new ServiceManager();
		serviceManager.setRestUtils(new RestUtils());
		setLogger(logger);
		setServices(serviceManager);
		interpreter.addCommandHandler(ADD_ACTIVITY_COMMAND, new AddActivity());
		interpreter.addCommandHandler(LIST_ACTIVITY_COMMAND, new ListActivity());
		interpreter.addCommandHandler(OPEN_DAY_COMMAND, new OpenDay());
		interpreter.addCommandHandler(ADD_BOOKING_COMMAND, new AddBooking());
		interpreter.addCommandHandler(DELETE_BOOKING_COMMAND, new DeleteBooking());
		interpreter.addCommandHandler(END_BOOKING_COMMAND, new EndBooking());
		interpreter.addCommandHandler(LIST_BOOKING_COMMAND, new ListBookings());
		interpreter.addCommandHandler(RUN_ANALYIS_COMMAND, new RunAnalysis());
		return interpreter;
	}

	private CLI createCLI(final CommandInterpreter interpreter) {
		CLI cli = new CLI();
		cli.setInterpreter(interpreter);
		return cli;
	}
}
