/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import de.lgblaumeiser.ptm.analysis.Analysis;
import de.lgblaumeiser.ptm.analysis.AnalysisResult;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.store.ObjectStore;

/**
 * An analysis to compute the amount of hours per a activity. The computer
 * calculates the percentage of an activity on the overall hours and maps these
 * percentages to the amount of 8 hours per booking day. This way, this fulfills
 * the requirements of the author concerning his time keeping.
 */
public class ProjectComputer implements Analysis {
    private ObjectStore<DayBookings> store;

    @Override
    public AnalysisResult analyze(final Collection<String> parameter) {
	LocalDate currentDay = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
	if (parameter.size() > 0) {
	    String date = get(parameter, 0) + "-01";
	    currentDay = LocalDate.parse(date);
	}
	AnalysisResult result = new AnalysisResult();
	result.setResult(asList("Id", "Total Hours", "Percentage", "Booking"));
	Duration totalMinutes = Duration.ZERO;
	int numberOfDays = 0;
	Map<String, Duration> activityToMinutesMap = newHashMap();
	do {
	    DayBookings currentBookings = store.retrieveByIndexKey(currentDay);
	    if (currentBookings != null && hasCompleteBookings(currentBookings)) {
		numberOfDays++;
		for (Booking currentBooking : currentBookings.getBookings()) {
		    Activity currentActivity = currentBooking.getActivity();
		    Duration accumulatedMinutes = activityToMinutesMap.get(currentActivity.getBookingNumber());
		    if (accumulatedMinutes == null) {
			accumulatedMinutes = Duration.ZERO;
		    }
		    Duration activityLength = currentBooking.calculateTimeSpan().getLengthInMinutes();
		    totalMinutes = totalMinutes.plus(activityLength);
		    accumulatedMinutes = accumulatedMinutes.plus(activityLength);
		    activityToMinutesMap.put(currentActivity.getBookingNumber(), accumulatedMinutes);
		}
	    }
	    currentDay = currentDay.plusDays(1);
	} while (!firstDayInMonth(currentDay));
	double totalNumberOfMinutes = 8.0 * 60.0 * numberOfDays;
	for (Entry<String, Duration> currentNumber : activityToMinutesMap.entrySet()) {
	    String number = currentNumber.getKey();
	    Duration totalMinutesId = currentNumber.getValue();
	    double percentage = (double) totalMinutesId.toMinutes() / (double) totalMinutes.toMinutes();
	    String percentageString = String.format("%.1f", percentage * 100.0);
	    Duration bookingMinutes = Duration.ofMinutes(Math.round(totalNumberOfMinutes * percentage));
	    result.setResult(
		    asList(number, formatDuration(totalMinutesId), percentageString, formatDuration(bookingMinutes)));
	}

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

    private String formatDuration(final Duration duration) {
	long minutes = duration.toMinutes();
	char pre = minutes < 0 ? '-' : ' ';
	minutes = Math.abs(minutes);
	return String.format("%c%02d,%02d", pre, minutes / 60, minutes % 60 * 10 / 6);
    }

    public void setStore(final ObjectStore<DayBookings> store) {
	this.store = store;
    }
}
