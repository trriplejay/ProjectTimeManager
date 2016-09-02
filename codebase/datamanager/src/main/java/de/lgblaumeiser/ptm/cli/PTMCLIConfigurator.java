/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.util.Properties;

import de.lgblaumeiser.ptm.cli.engine.CommandInterpreter;
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
    public CLI configure() {
	ObjectStore<DayBookings> bookingStore = createBookingFileStore();
	ObjectStore<ActivityModel> activityStore = createActivityFileStore();
	BookingService bookingService = createBookingService();
	ActivityService activityService = createActivityService(activityStore);
	CommandInterpreter interpreter = createCommandInterpreter(bookingStore, activityStore, bookingService,
		activityService);
	return createCLI(interpreter);
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
	    final ActivityService activityService) {
	CommandInterpreter interpreter = new CommandInterpreter();
	// add handler
	return interpreter;
    }

    private CLI createCLI(final CommandInterpreter interpreter) {
	CLI cli = new CLI();
	cli.setInterpreter(interpreter);
	return cli;
    }
}
