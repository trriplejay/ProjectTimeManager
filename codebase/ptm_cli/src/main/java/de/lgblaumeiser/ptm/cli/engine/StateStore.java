/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine;

import static com.google.common.base.Preconditions.checkState;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.time.LocalDate;

/**
 * Store to save common state objects for all handlers
 */
public class StateStore {
	private LocalDate currentDay;

	public LocalDate getCurrentDay() {
		checkState(currentDay != null);
		return currentDay;
	}

	public void setCurrentDay(final LocalDate currentDay) {
		this.currentDay = currentDay;
	}

	public String getCurrentDayString() {
		return currentDay.format(ISO_LOCAL_DATE);
	}
}
