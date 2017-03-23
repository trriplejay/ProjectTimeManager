/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.Duration.ZERO;
import static java.time.Duration.ofMinutes;
import static java.time.YearMonth.now;
import static java.time.YearMonth.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Collection;

import de.lgblaumeiser.ptm.analysis.Analysis;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * An analysis that counts all hours in the month given as parameter
 */
public class HourComputer implements Analysis {
	private ObjectStore<Booking> store;

	private static final boolean SITEETAS = false;
	private static final boolean SITESI = true;

	@Override
	public Collection<Collection<Object>> analyze(final Collection<String> parameter) {
		YearMonth requestedMonth = now();
		if (parameter.size() > 0) {
			requestedMonth = parse(get(parameter, 0));
		}
		Collection<Collection<Object>> result = newArrayList();
		result.add(asList("Work Day", "Starttime", "Endtime", "Presence", "Worktime", "Overtime"));
		Duration overtime = ZERO;
		LocalDate currentday = requestedMonth.atDay(1);
		while (!currentday.isAfter(requestedMonth.atEndOfMonth())) {
			Collection<Booking> currentBookings = getBookingsForDay(currentday);
			if (hasCompleteBookings(currentBookings)) {
				String day = currentday.format(ISO_LOCAL_DATE);
				LocalTime starttime = getFirst(currentBookings, null).getStarttime();
				LocalTime endtime = getLast(currentBookings).getEndtime();
				Duration presence = calculatePresence(currentBookings);
				Duration worktime = ZERO;
				if (SITEETAS) {
					worktime = calculateWorktimeETAS(presence);
				} else if (SITESI) {
					worktime = calculateWorktimeSchwieberdingen(presence);
				}
				Duration currentOvertime = calculateOvertime(worktime, currentday);
				overtime = overtime.plus(currentOvertime);
				result.add(asList(day, starttime.format(ofPattern("HH:mm")), endtime.format(ofPattern("HH:mm")),
						formatDuration(presence), formatDuration(worktime), formatDuration(overtime)));
			}
			currentday = currentday.plusDays(1);
		}
		return result;
	}

	private Collection<Booking> getBookingsForDay(final LocalDate currentday) {
		return store.retrieveAll().stream().filter(b -> b.getBookingday().equals(currentday))
				.sorted((b1, b2) -> b1.getStarttime().compareTo(b2.getStarttime())).collect(toList());
	}

	private boolean hasCompleteBookings(final Collection<Booking> bookings) {
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
		Duration minutes = worktime;
		if (isWeekDay(day)) {
			minutes = minutes.minus(Duration.ofMinutes(480)); // Overtime is
			// time after 8
			// hours
		}
		return minutes;
	}

	private boolean isWeekDay(final LocalDate day) {
		return !(day.getDayOfWeek() == SATURDAY || day.getDayOfWeek() == SUNDAY);
	}

	private Duration calculateWorktimeETAS(final Duration presence) {
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
		return ofMinutes(minutes);
	}

	private Duration calculateWorktimeSchwieberdingen(final Duration presence) {
		long minutes = presence.toMinutes();
		if (minutes >= 45) {
			minutes -= 45;
		}
		return ofMinutes(minutes);
	}

	private Duration calculatePresence(Collection<Booking> bookings) {
		Duration minutes = ZERO;
		for (Booking current : bookings) {
			minutes = minutes.plus(current.calculateTimeSpan().getLengthInMinutes());
		}
		return minutes;
	}

	private String formatDuration(final Duration duration) {
		long minutes = duration.toMinutes();
		char pre = minutes < 0 ? '-' : ' ';
		minutes = abs(minutes);
		return format("%c%02d:%02d", pre, minutes / 60, minutes % 60);
	}

	public void setStore(final ObjectStore<Booking> store) {
		this.store = store;
	}
}
