/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli;

import com.beust.jcommander.JCommander;
import de.lgblaumeiser.ptm.analysis.DataAnalysisService;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.cli.engine.PrettyPrinter;
import de.lgblaumeiser.ptm.cli.engine.ServiceManager;
import de.lgblaumeiser.ptm.cli.engine.handler.*;
import de.lgblaumeiser.ptm.cli.rest.*;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.service.BookingService;
import de.lgblaumeiser.ptm.datamanager.service.BookingServiceImpl;
import de.lgblaumeiser.ptm.store.ObjectStore;

import java.io.File;

import static java.lang.System.getProperty;
import static java.lang.System.setProperty;

/**
 * The configuration object that creates the application structure
 */
public class PTMCLIConfigurator {
	private static final String ADD_ACTIVITY_COMMAND = "add_activity";
	private static final String ADD_ACTIVITY_COMMAND_ABBRV = "aa";
	private static final String LIST_ACTIVITY_COMMAND = "list_activities";
	private static final String LIST_ACTIVITY_COMMAND_ABBRV = "la";
	private static final String ADD_BOOKING_COMMAND = "add_booking";
	private static final String ADD_BOOKING_COMMAND_ABBRV = "ab";
	private static final String DELETE_BOOKING_COMMAND = "delete_booking";
	private static final String DELETE_BOOKING_COMMAND_ABBRV = "db";
	private static final String END_BOOKING_COMMAND = "end_booking";
	private static final String END_BOOKING_COMMAND_ABBRV = "eb";
	private static final String LIST_BOOKING_COMMAND = "list_bookings";
	private static final String LIST_BOOKING_COMMAND_ABBRV = "lb";
	private static final String HOURS_ANALYSIS_COMMAND = "hour_analysis";
	private static final String HOURS_ANALYSIS_COMMAND_ABBRV = "ha";
	private static final String PROJECTS_ANALYSIS_COMMAND = "project_analysis";
	private static final String PROJECTS_ANALYSIS_COMMAND_ABBRV = "pa";

	public CLI configure() {
		setProperty("filestore.folder", new File(getProperty("user.home"), ".ptm").getAbsolutePath());
		RestBookingStore bookingStore = new RestBookingStore();
		ObjectStore<Activity> activityStore = new RestActivityStore();
		BookingService bookingService = new BookingServiceImpl().setBookingStore(bookingStore);
		DataAnalysisService analysisService = new RestAnalysisService();
		ServiceManager manager = createServiceManager(bookingStore, activityStore, bookingService, analysisService);
		RestBaseService.setRestUtils(new RestUtils().configure());
		RestBaseService.setServices(manager);
		return createCLI(createCommandInterpreter(manager));
	}

	private ServiceManager createServiceManager(final RestBookingStore bookingStore,
			final ObjectStore<Activity> activityStore, final BookingService bookingService,
			final DataAnalysisService analysisService) {
		ServiceManager serviceManager = new ServiceManager();
		serviceManager.setActivityStore(activityStore);
		serviceManager.setBookingService(bookingService);
		serviceManager.setBookingsStore(bookingStore);
		serviceManager.setAnalysisService(analysisService);
		return serviceManager;
	}

	private JCommander createCommandInterpreter(final ServiceManager serviceManager) {
        StdoutLogger logger = new StdoutLogger();
        AbstractCommandHandler.setLogger(logger);
        AbstractCommandHandler.setServices(serviceManager);
        AbstractCommandHandler.setPrinter(new PrettyPrinter().setLogger(logger));
        JCommander jc = JCommander.newBuilder()
                .addObject(new MainParameters())
				.addCommand(ADD_ACTIVITY_COMMAND, new AddActivity())
				.addCommand(ADD_ACTIVITY_COMMAND_ABBRV, new AddActivity())
				.addCommand(LIST_ACTIVITY_COMMAND, new ListActivity())
				.addCommand(LIST_ACTIVITY_COMMAND_ABBRV, new ListActivity())
				.addCommand(ADD_BOOKING_COMMAND, new AddBooking())
				.addCommand(ADD_BOOKING_COMMAND_ABBRV, new AddBooking())
				.addCommand(DELETE_BOOKING_COMMAND, new DeleteBooking())
				.addCommand(DELETE_BOOKING_COMMAND_ABBRV, new DeleteBooking())
				.addCommand(END_BOOKING_COMMAND, new EndBooking())
				.addCommand(END_BOOKING_COMMAND_ABBRV, new EndBooking())
				.addCommand(LIST_BOOKING_COMMAND, new ListBookings())
				.addCommand(LIST_BOOKING_COMMAND_ABBRV, new ListBookings())
				.addCommand(HOURS_ANALYSIS_COMMAND, new RunHourAnalysis())
				.addCommand(HOURS_ANALYSIS_COMMAND_ABBRV, new RunHourAnalysis())
				.addCommand(PROJECTS_ANALYSIS_COMMAND, new RunProjectAnalysis())
				.addCommand(PROJECTS_ANALYSIS_COMMAND_ABBRV, new RunProjectAnalysis())
                .build();
		return jc;
	}

	private CLI createCLI(final JCommander interpreter) {
		CLI cli = new CLI();
		cli.setInterpreter(interpreter);
		return cli;
	}
}
