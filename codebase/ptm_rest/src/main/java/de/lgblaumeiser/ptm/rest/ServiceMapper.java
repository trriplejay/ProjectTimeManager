/*
 * Copyright 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.rest;

import static java.lang.System.getProperty;
import static java.lang.System.setProperty;

import java.io.File;

import org.springframework.stereotype.Component;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.ptm.datamanager.service.BookingService;
import de.lgblaumeiser.ptm.datamanager.service.BookingServiceImpl;
import de.lgblaumeiser.ptm.store.ObjectStore;
import de.lgblaumeiser.ptm.store.filesystem.FileStore;
import de.lgblaumeiser.ptm.store.filesystem.FilesystemAbstraction;
import de.lgblaumeiser.ptm.store.filesystem.FilesystemAbstractionImpl;

/**
 * Small bean that creates and configures the services needed by the Rest
 * interface
 */
@Component
public class ServiceMapper {
	private final ObjectStore<Activity> activityStore;

	private final ObjectStore<DayBookings> bookingStore;
	private final BookingService bookingService;

	public ServiceMapper() {
		setProperty("filestore.folder", new File(getProperty("user.home"), ".ptm").getAbsolutePath());
		FilesystemAbstraction filesystemAbstraction = new FilesystemAbstractionImpl();
		activityStore = new FileStore<Activity>() {
		}.setFilesystemAccess(filesystemAbstraction);
		bookingStore = new FileStore<DayBookings>() {
		}.setFilesystemAccess(filesystemAbstraction);
		bookingService = new BookingServiceImpl();
	}

	public ObjectStore<Activity> activityStore() {
		return activityStore;
	}

	public ObjectStore<DayBookings> bookingStore() {
		return bookingStore;
	}

	public BookingService bookingService() {
		return bookingService;
	}
}
