/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.util.Properties;

import de.lgblaumeiser.ptm.analysis.DataAnalysisService;
import de.lgblaumeiser.ptm.analysis.DataAnalysisServiceImpl;
import de.lgblaumeiser.ptm.analysis.analyzer.HourComputer;
import de.lgblaumeiser.ptm.analysis.analyzer.ProjectComputer;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
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
import de.lgblaumeiser.ptm.datamanager.model.ActivityModel;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.ptm.datamanager.service.ActivityService;
import de.lgblaumeiser.ptm.datamanager.service.ActivityServiceImpl;
import de.lgblaumeiser.ptm.datamanager.service.BookingService;
import de.lgblaumeiser.ptm.datamanager.service.BookingServiceImpl;
import de.lgblaumeiser.store.ObjectStore;
import de.lgblaumeiser.store.filesystem.FileStore;
import de.lgblaumeiser.store.filesystem.FileSystemAbstractionImpl;

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
	private static final String ANALYSIS_HOURS_ID = "HOURS";
	private static final String ANALYSIS_PROJECTS_ID = "PROJECTS";

	public CLI configure() {
		ObjectStore<DayBookings> bookingStore = createBookingFileStore();
		ObjectStore<ActivityModel> activityStore = createActivityFileStore();
		BookingService bookingService = createBookingService();
		ActivityService activityService = createActivityService(activityStore);
		DataAnalysisService analysisService = createAnalysisService(bookingStore);
		CommandInterpreter interpreter = createCommandInterpreter(bookingStore, activityStore, bookingService,
				activityService, analysisService);
		return createCLI(interpreter);
	}

	private DataAnalysisService createAnalysisService(final ObjectStore<DayBookings> store) {
		DataAnalysisServiceImpl service = new DataAnalysisServiceImpl();
		HourComputer hourComputer = new HourComputer();
		hourComputer.setStore(store);
		service.addAnalysis(ANALYSIS_HOURS_ID, hourComputer);
		ProjectComputer projectComputer = new ProjectComputer();
		projectComputer.setStore(store);
		service.addAnalysis(ANALYSIS_PROJECTS_ID, projectComputer);
		return service;
	}

	private ObjectStore<DayBookings> createBookingFileStore() {
		FileStore<DayBookings> store = new FileStore<DayBookings>();
		Properties props = new Properties();
		props.setProperty(FileStore.INDEX_KEY, "day");
		props.setProperty(FileStore.CLASS_KEY, DayBookings.class.getName());
		File storagePath = getStoragePath();
		props.setProperty(FileStore.STORAGE_PATH_KEY, storagePath.getAbsolutePath());
		props.setProperty(FileStore.FILE_ENDING_KEY, "bkjs");
		store.configure(props);
		store.setFilesystemAccess(new FileSystemAbstractionImpl());
		return store;
	}

	private ObjectStore<ActivityModel> createActivityFileStore() {
		FileStore<ActivityModel> store = new FileStore<ActivityModel>();
		Properties props = new Properties();
		props.setProperty(FileStore.INDEX_KEY, "modelId");
		props.setProperty(FileStore.CLASS_KEY, ActivityModel.class.getName());
		File storagePath = getStoragePath();
		props.setProperty(FileStore.STORAGE_PATH_KEY, storagePath.getAbsolutePath());
		props.setProperty(FileStore.FILE_ENDING_KEY, "amjs");
		store.configure(props);
		store.setFilesystemAccess(new FileSystemAbstractionImpl());
		return store;
	}

	private File getStoragePath() {
		File homepath = new File(System.getProperty("user.home"));
		checkState(homepath.isDirectory() && homepath.exists());
		File applicationPath = new File(homepath, ".ptm");
		if (!applicationPath.exists()) {
			checkState(applicationPath.mkdir());
		}
		return applicationPath;
	}

	private BookingService createBookingService() {
		return new BookingServiceImpl();
	}

	private ActivityService createActivityService(final ObjectStore<ActivityModel> activityStore) {
		ActivityServiceImpl service = new ActivityServiceImpl();
		ActivityModel model = activityStore.retrieveByIndexKey(ActivityModel.ACTIVITY_MODEL_ID);
		if (model != null) {
			service.setActivityStore(model);
		} else {
			service.setActivityStore(new ActivityModel());
		}
		return service;
	}

	private CommandInterpreter createCommandInterpreter(final ObjectStore<DayBookings> bookingStore,
			final ObjectStore<ActivityModel> activityStore, final BookingService bookingService,
			final ActivityService activityService, final DataAnalysisService analysisService) {
		CommandInterpreter interpreter = new CommandInterpreter();
		CommandLogger logger = new StdoutLogger();
		ServiceManager serviceManager = new ServiceManager();
		serviceManager.setActivityService(activityService);
		serviceManager.setActivityStore(activityStore);
		serviceManager.setBookingService(bookingService);
		serviceManager.setBookingsStore(bookingStore);
		serviceManager.setAnalysisService(analysisService);
		AbstractCommandHandler.setLogger(logger);
		AbstractCommandHandler.setServices(serviceManager);
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
