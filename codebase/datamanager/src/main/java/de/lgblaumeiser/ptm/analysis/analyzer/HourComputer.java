/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

import com.google.common.collect.Iterables;

import de.lgblaumeiser.ptm.analysis.Analysis;
import de.lgblaumeiser.ptm.analysis.AnalysisResult;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.store.ObjectStore;

/**
 * An analysis that counts all hours in the month given as parameter
 */
public class HourComputer implements Analysis {
    private ObjectStore<DayBookings> store;

    @Override
    public AnalysisResult analyze(final Collection<String> parameter) {
	LocalDate currentDay = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
	if (parameter.size() > 0) {
	    String date = Iterables.get(parameter, 0) + "-01";
	    currentDay = LocalDate.parse(date);
	}
	Duration overtime = Duration.ofSeconds(0);
	do {
	    DayBookings currentBookings = store.retrieveByIndexKey(currentDay);
	    if (currentBookings != null && hasCompleteBookings(currentBookings)) {
		overtime = overtime.plus(computeOvertimeInMinutes(currentBookings));
	    }
	    currentDay = currentDay.plusDays(1);
	} while (!firstDayInMonth(currentDay));
	AnalysisResult result = new AnalysisResult();
	result.setResult("Aggregated Hours", calculateOvertimeHours(overtime));
	return result;
    }

    private String calculateOvertimeHours(final Duration overtime) {
	long minutes = overtime.toMinutes();
	return String.format("%02d:%02d", minutes / 60, minutes % 60);
    }

    private boolean firstDayInMonth(final LocalDate currentDay) {
	return currentDay.getDayOfMonth() == 1;
    }

    private Duration computeOvertimeInMinutes(final DayBookings currentBookings) {
	Collection<Booking> bookings = currentBookings.getBookings();
	long minutes = 0;
	for (Booking current : bookings) {
	    minutes += current.calculateTimeSpan().getLengthInMinutes();
	}
	if (minutes >= 585) { // longer than 9,75 hours => -45 minutes break
	    minutes -= 45;
	} else if (minutes >= 570) { // longer than 9,5 hours => 9 hours
	    minutes = 540;
	} else if (minutes >= 390) { // longer than 6,5 hours => -30 minutes
	    minutes -= 30;
	} else if (minutes >= 360) { // longer than 6 hours => 6 hours
	    minutes = 360;
	}
	if (isWeekDay(currentBookings.getDay())) {
	    minutes -= 480; // Overtime is time after 8 hours
	}
	return Duration.ofMinutes(minutes);
    }

    private boolean isWeekDay(final LocalDate day) {
	return !(day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY);
    }

    private boolean hasCompleteBookings(final DayBookings currentBookings) {
	Collection<Booking> bookings = currentBookings.getBookings();
	if (bookings.isEmpty()) {
	    return false;
	}
	for (Booking current : bookings) {
	    if (!current.hasEndtime()) {
		return false;
	    }
	}
	return true;
    }

    public void setStore(final ObjectStore<DayBookings> store) {
	this.store = store;
    }
}
