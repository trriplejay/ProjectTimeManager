/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli;

import com.beust.jcommander.JCommander;
import de.lgblaumeiser.ptm.analysis.DataAnalysisService;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.cli.engine.CommandLogger;
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
	private static final String LIST_ACTIVITY_COMMAND = "list_activities";
	private static final String ADD_BOOKING_COMMAND = "add_booking";
	private static final String DELETE_BOOKING_COMMAND = "delete_booking";
	private static final String END_BOOKING_COMMAND = "end_booking";
	private static final String LIST_BOOKING_COMMAND = "list_bookings";
	private static final String HOURS_ANALYSIS_COMMAND = "hour_analysis";
	private static final String PROJECTS_ANALYSIS_COMMAND = "project_analysis";

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
        CommandLogger logger = new StdoutLogger();
        AbstractCommandHandler.setLogger(logger);
        AbstractCommandHandler.setServices(serviceManager);
        AbstractCommandHandler.setPrinter(new PrettyPrinter().setLogger(logger));
        JCommander jc = JCommander.newBuilder()
                .addObject(new MainParameters())
                .addCommand(ADD_ACTIVITY_COMMAND, new AddActivity())
                .addCommand(LIST_ACTIVITY_COMMAND, new ListActivity())
                .addCommand(ADD_BOOKING_COMMAND, new AddBooking())
                .addCommand(DELETE_BOOKING_COMMAND, new DeleteBooking())
                .addCommand(END_BOOKING_COMMAND, new EndBooking())
                .addCommand(LIST_BOOKING_COMMAND, new ListBookings())
                .addCommand(HOURS_ANALYSIS_COMMAND, new RunHourAnalysis())
                .addCommand(PROJECTS_ANALYSIS_COMMAND, new RunProjectAnalysis())
                .build();
		return jc;
	}

	private CLI createCLI(final JCommander interpreter) {
		CLI cli = new CLI();
		cli.setInterpreter(interpreter);
		return cli;
	}
}
