/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine;

import de.lgblaumeiser.ptm.analysis.DataAnalysisService;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.service.BookingService;
import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * Small class that allows to access the services needed by the command handler
 * implementations.
 */
public class ServiceManager {
	private BookingService bookingService;
	private DataAnalysisService analysisService;
	private ObjectStore<Activity> activityStore;
	private ObjectStore<Booking> bookingsStore;
	private final StateStore stateStore = new StateStore();

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(final BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public DataAnalysisService getAnalysisService() {
		return analysisService;
	}

	public void setAnalysisService(final DataAnalysisService analysisService) {
		this.analysisService = analysisService;
	}

	public ObjectStore<Activity> getActivityStore() {
		return activityStore;
	}

	public void setActivityStore(final ObjectStore<Activity> activityStore) {
		this.activityStore = activityStore;
	}

	public ObjectStore<Booking> getBookingsStore() {
		return bookingsStore;
	}

	public void setBookingsStore(final ObjectStore<Booking> bookingsStore) {
		this.bookingsStore = bookingsStore;
	}

	public StateStore getStateStore() {
		return stateStore;
	}
}
