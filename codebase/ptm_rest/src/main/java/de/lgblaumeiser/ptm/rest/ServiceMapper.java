/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.rest;

import de.lgblaumeiser.ptm.analysis.DataAnalysisService;
import de.lgblaumeiser.ptm.analysis.DataAnalysisServiceImpl;
import de.lgblaumeiser.ptm.analysis.analyzer.HourComputer;
import de.lgblaumeiser.ptm.analysis.analyzer.ProjectComputer;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.service.BookingService;
import de.lgblaumeiser.ptm.datamanager.service.BookingServiceImpl;
import de.lgblaumeiser.ptm.store.ObjectStore;
import de.lgblaumeiser.ptm.store.ZipBackupRestore;
import de.lgblaumeiser.ptm.store.filesystem.FileStore;
import de.lgblaumeiser.ptm.store.filesystem.FilesystemAbstraction;
import de.lgblaumeiser.ptm.store.filesystem.FilesystemAbstractionImpl;
import org.springframework.stereotype.Component;

import java.io.File;

import static java.lang.System.getProperty;
import static java.lang.System.setProperty;

/**
 * Small bean that creates and configures the services needed by the Rest
 * interface
 */
@Component
public class ServiceMapper {
	private static final String ANALYSIS_HOURS_ID = "HOURS";
	private static final String ANALYSIS_PROJECTS_ID = "PROJECTS";

	private final FileStore<Activity> activityStore;

	private final FileStore<Booking> bookingStore;
	private final BookingService bookingService;
	
	private final ZipBackupRestore backupService;

	private final DataAnalysisService analysisService;

	public ServiceMapper() {
		setProperty("filestore.folder", new File(getProperty("user.home"), ".ptm").getAbsolutePath());
		FilesystemAbstraction filesystemAbstraction = new FilesystemAbstractionImpl();
		activityStore = new FileStore<Activity>(filesystemAbstraction) {};
		bookingStore = new FileStore<Booking>(filesystemAbstraction) {};
		bookingService = new BookingServiceImpl(bookingStore);
		backupService = new ZipBackupRestore(activityStore, bookingStore);
		analysisService = createAnalysisService(bookingStore);
	}

	private DataAnalysisService createAnalysisService(final ObjectStore<Booking> store) {
		HourComputer hourComputer = new HourComputer(store);
		ProjectComputer projectComputer = new ProjectComputer(store);
		return new DataAnalysisServiceImpl()
				.addAnalysis(ANALYSIS_HOURS_ID, hourComputer)
				.addAnalysis(ANALYSIS_PROJECTS_ID, projectComputer);
	}

	public ObjectStore<Activity> activityStore() {
		return activityStore;
	}

	public ObjectStore<Booking> bookingStore() {
		return bookingStore;
	}

	public BookingService bookingService() {
		return bookingService;
	}

	public ZipBackupRestore backupService() {
		return backupService;
	}
	
	public DataAnalysisService analysisService() {
		return analysisService;
	}
}
