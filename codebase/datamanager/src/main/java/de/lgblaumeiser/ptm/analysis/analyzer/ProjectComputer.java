/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.String.format;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.Duration.ZERO;
import static java.time.Duration.ofHours;
import static java.time.YearMonth.from;
import static java.time.YearMonth.now;
import static java.time.YearMonth.parse;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.lgblaumeiser.ptm.analysis.Analysis;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * An analysis to compute the amount of hours per a activity. The computer
 * calculates the percentage of an activity on the overall hours and maps these
 * percentages to the amount of 8 hours per booking day. This way, this fulfills
 * the requirements of the author concerning his time keeping.
 */
public class ProjectComputer implements Analysis {
	private ObjectStore<Booking> store;

	@Override
	public Collection<Collection<Object>> analyze(final Collection<String> parameter) {
		YearMonth requestedMonth = now();
		if (parameter.size() > 0) {
			String date = get(parameter, 0) + "-01";
			requestedMonth = parse(date);
		}
		Collection<Collection<Object>> result = newArrayList();
		result.add(asList("Id", "Total", "%", "Book"));
		Duration totalMinutes = ZERO;
		Set<LocalDate> daysWorked = newHashSet();
		Map<String, Duration> activityToMinutesMap = newHashMap();
		for (Booking current : getBookingsForMonth(requestedMonth)) {
			if (current.hasEndtime()) {
				LocalDate bookingDate = current.getBookingday();
				if (!bookingDate.getDayOfWeek().equals(SATURDAY) && !bookingDate.getDayOfWeek().equals(SUNDAY)) {
					daysWorked.add(bookingDate);
				}
				Activity currentActivity = current.getActivity();
				Duration accumulatedMinutes = activityToMinutesMap.get(currentActivity.getBookingNumber());
				if (accumulatedMinutes == null) {
					accumulatedMinutes = ZERO;
				}
				Duration activityLength = current.calculateTimeSpan().getLengthInMinutes();
				totalMinutes = totalMinutes.plus(activityLength);
				accumulatedMinutes = accumulatedMinutes.plus(activityLength);
				activityToMinutesMap.put(currentActivity.getBookingNumber(), accumulatedMinutes);
			}
		}
		int numberOfDays = daysWorked.size();
		double totalNumberOfMinutes = 8.0 * 60.0 * numberOfDays;
		int targetHours = 8 * numberOfDays;
		for (Entry<String, Duration> currentNumber : activityToMinutesMap.entrySet()) {
			String number = currentNumber.getKey();
			Duration totalMinutesId = currentNumber.getValue();
			double percentage = (double) totalMinutesId.toMinutes() / (double) totalMinutes.toMinutes();
			String percentageString = format("%2.1f", percentage * 100.0);
			Duration bookingHours = ofHours(round(totalNumberOfMinutes * percentage / 60.0));
			targetHours -= bookingHours.toHours();
			result.add(asList(number, formatDuration(totalMinutesId), percentageString,
					format("%d", bookingHours.toHours())));
		}
		if (targetHours != 0) {
			result.add(asList("Left", EMPTY, EMPTY, format("%d", targetHours)));
		}

		return result;
	}

	private Collection<Booking> getBookingsForMonth(final YearMonth month) {
		return store.retrieveAll().stream().filter(b -> month.equals(from(b.getBookingday()))).collect(toList());
	}

	private String formatDuration(final Duration duration) {
		long minutes = duration.toMinutes();
		char pre = minutes < 0 ? '-' : ' ';
		minutes = abs(minutes);
		return format("%c%02d,%02d", pre, minutes / 60, minutes % 60 * 10 / 6);
	}

	public void setStore(final ObjectStore<Booking> store) {
		this.store = store;
	}
}
