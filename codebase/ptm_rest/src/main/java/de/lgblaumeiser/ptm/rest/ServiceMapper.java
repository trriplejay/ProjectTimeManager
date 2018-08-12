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

	private final ObjectStore<Activity> activityStore;

	private final ObjectStore<Booking> bookingStore;
	private final BookingService bookingService;

	private final DataAnalysisService analysisService;

	public ServiceMapper() {
		setProperty("filestore.folder", new File(getProperty("user.home"), ".ptm").getAbsolutePath());
		FilesystemAbstraction filesystemAbstraction = new FilesystemAbstractionImpl();
		activityStore = new FileStore<Activity>() {
		}.setFilesystemAccess(filesystemAbstraction);
		bookingStore = new FileStore<Booking>() {
		}.setFilesystemAccess(filesystemAbstraction);
		bookingService = new BookingServiceImpl().setBookingStore(bookingStore);
		analysisService = createAnalysisService(bookingStore);
	}

	private DataAnalysisService createAnalysisService(final ObjectStore<Booking> store) {
		DataAnalysisServiceImpl service = new DataAnalysisServiceImpl();
		HourComputer hourComputer = new HourComputer();
		hourComputer.setStore(store);
		service.addAnalysis(ANALYSIS_HOURS_ID, hourComputer);
		ProjectComputer projectComputer = new ProjectComputer();
		projectComputer.setStore(store);
		service.addAnalysis(ANALYSIS_PROJECTS_ID, projectComputer);
		return service;
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

	public DataAnalysisService analysisService() {
		return analysisService;
	}
}
