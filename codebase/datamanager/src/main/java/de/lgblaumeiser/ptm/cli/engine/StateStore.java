/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine;

import static com.google.common.base.Preconditions.checkState;

import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * Store to save common state objects for all handlers
 */
public class StateStore {
    private DayBookings currentDay;

    public DayBookings getCurrentDay() {
	checkState(currentDay != null);
	return currentDay;
    }

    public void setCurrentDay(final DayBookings currentDay) {
	this.currentDay = currentDay;
    }
}
