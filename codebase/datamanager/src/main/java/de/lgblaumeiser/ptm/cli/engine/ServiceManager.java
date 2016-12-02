/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine;

import de.lgblaumeiser.ptm.analysis.DataAnalysisService;
import de.lgblaumeiser.ptm.datamanager.model.ActivityModel;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.ptm.datamanager.service.ActivityService;
import de.lgblaumeiser.ptm.datamanager.service.BookingService;
import de.lgblaumeiser.store.ObjectStore;

/**
 * Small class that allows to access the services needed by the command handler
 * implementations.
 */
public class ServiceManager {
	private ActivityService activityService;
	private BookingService bookingService;
	private DataAnalysisService analysisService;
	private ObjectStore<ActivityModel> activityStore;
	private ObjectStore<DayBookings> bookingsStore;
	private final StateStore stateStore = new StateStore();

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(final ActivityService activityService) {
		this.activityService = activityService;
	}

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

	public ObjectStore<ActivityModel> getActivityStore() {
		return activityStore;
	}

	public void setActivityStore(final ObjectStore<ActivityModel> activityStore) {
		this.activityStore = activityStore;
	}

	public ObjectStore<DayBookings> getBookingsStore() {
		return bookingsStore;
	}

	public void setBookingsStore(final ObjectStore<DayBookings> bookingsStore) {
		this.bookingsStore = bookingsStore;
	}

	public StateStore getStateStore() {
		return stateStore;
	}
}
