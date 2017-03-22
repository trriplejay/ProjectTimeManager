/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.String.format;
import static java.time.Duration.ZERO;
import static java.time.Duration.ofHours;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static java.time.LocalDate.parse;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import de.lgblaumeiser.ptm.analysis.Analysis;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * An analysis to compute the amount of hours per a activity. The computer
 * calculates the percentage of an activity on the overall hours and maps these
 * percentages to the amount of 8 hours per booking day. This way, this fulfills
 * the requirements of the author concerning his time keeping.
 */
public class ProjectComputer implements Analysis {
	private ObjectStore<DayBookings> store;

	@Override
	public Collection<Collection<Object>> analyze(final Collection<String> parameter) {
		LocalDate currentDay = of(now().getYear(), now().getMonthValue(), 1);
		if (parameter.size() > 0) {
			String date = get(parameter, 0) + "-01";
			currentDay = parse(date);
		}
		Collection<Collection<Object>> result = newArrayList();
		result.add(asList("Id", "Total", "%", "Book"));
		Duration totalMinutes = ZERO;
		int numberOfDays = 0;
		Map<String, Duration> activityToMinutesMap = newHashMap();
		do {
			Optional<DayBookings> currentBookings = getBookingsForDay(currentDay);
			if (currentBookings.isPresent() && hasCompleteBookings(currentBookings.get())) {
				numberOfDays++;
				for (Booking currentBooking : currentBookings.get().getBookings()) {
					Activity currentActivity = currentBooking.getActivity();
					Duration accumulatedMinutes = activityToMinutesMap.get(currentActivity.getBookingNumber());
					if (accumulatedMinutes == null) {
						accumulatedMinutes = ZERO;
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

	private Optional<DayBookings> getBookingsForDay(final LocalDate currentDay) {
		return store.retrieveAll().stream().filter(d -> currentDay.equals(d.getDay())).findFirst();
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
		minutes = abs(minutes);
		return format("%c%02d,%02d", pre, minutes / 60, minutes % 60 * 10 / 6);
	}

	public void setStore(final ObjectStore<DayBookings> store) {
		this.store = store;
	}
}
