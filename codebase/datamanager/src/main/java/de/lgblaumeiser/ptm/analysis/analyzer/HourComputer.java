/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import static com.google.common.collect.Iterables.get;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.Arrays.asList;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

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
	    String date = get(parameter, 0) + "-01";
	    currentDay = LocalDate.parse(date);
	}
	AnalysisResult result = new AnalysisResult();
	result.setResult(asList("Work Day", "Presence", "Worktime", "Overtime"));
	Duration overtime = Duration.ofSeconds(0);
	do {
	    DayBookings currentBookings = store.retrieveByIndexKey(currentDay);
	    if (currentBookings != null && hasCompleteBookings(currentBookings)) {
		String day = currentDay.format(ISO_LOCAL_DATE);
		Duration presence = calculatePresence(currentBookings);
		Duration worktime = calculateWorktime(presence);
		Duration currentOvertime = calculateOvertime(worktime, currentDay);
		overtime = overtime.plus(currentOvertime);
		result.setResult(
			asList(day, formatDuration(presence), formatDuration(worktime), formatDuration(overtime)));
	    }
	    currentDay = currentDay.plusDays(1);
	} while (!firstDayInMonth(currentDay));
	return result;
    }

    private boolean firstDayInMonth(final LocalDate currentDay) {
	return currentDay.getDayOfMonth() == 1;
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

    private Duration calculateOvertime(final Duration worktime, final LocalDate day) {
	long minutes = worktime.toMinutes();
	if (isWeekDay(day)) {
	    minutes -= 480; // Overtime is time after 8 hours
	}
	return Duration.ofMinutes(minutes);
    }

    private boolean isWeekDay(final LocalDate day) {
	return !(day.getDayOfWeek() == SATURDAY || day.getDayOfWeek() == SUNDAY);
    }

    private Duration calculateWorktime(final Duration presence) {
	long minutes = presence.toMinutes();
	if (minutes >= 585) { // longer than 9,75 hours => -45 minutes break
	    minutes -= 45;
	} else if (minutes >= 570) { // longer than 9,5 hours => 9 hours
	    minutes = 540;
	} else if (minutes >= 390) { // longer than 6,5 hours => -30 minutes
	    minutes -= 30;
	} else if (minutes >= 360) { // longer than 6 hours => 6 hours
	    minutes = 360;
	}
	return Duration.ofMinutes(minutes);
    }

    private Duration calculatePresence(final DayBookings currentBookings) {
	Collection<Booking> bookings = currentBookings.getBookings();
	long minutes = 0;
	for (Booking current : bookings) {
	    minutes += current.calculateTimeSpan().getLengthInMinutes();
	}
	return Duration.ofMinutes(minutes);
    }

    private String formatDuration(final Duration duration) {
	long minutes = duration.toMinutes();
	char pre = minutes < 0 ? '-' : ' ';
	minutes = Math.abs(minutes);
	return String.format("%c%02d:%02d", pre, minutes / 60, minutes % 60);
    }

    public void setStore(final ObjectStore<DayBookings> store) {
	this.store = store;
    }
}
