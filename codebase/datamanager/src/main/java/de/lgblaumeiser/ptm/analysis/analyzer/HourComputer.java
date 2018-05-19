/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import java.util.Collection;
import java.util.stream.Collectors;

import de.lgblaumeiser.ptm.analysis.Analysis;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;
import org.apache.commons.lang3.StringUtils;

/**
 * An analysis that counts all hours in the month given as parameter
 */
public class HourComputer implements Analysis {
	private static final String BREAKTIME_APPENDIX_NOT_VALID = "TOSHORT!!!";
	private ObjectStore<Booking> store;

	@Override
	public Collection<Collection<Object>> analyze(final Collection<String> parameter) {
		YearMonth requestedMonth = YearMonth.now();
		if (parameter.size() > 0) {
			requestedMonth = YearMonth.parse(Iterables.get(parameter, 0));
		}
		Collection<Collection<Object>> result = Lists.newArrayList();
		result.add(Arrays.asList("Work Day", "Starttime", "Endtime", "Presence", "Worktime", "Breaktime", "Time Account"));
		Duration overtime = Duration.ZERO;
		LocalDate currentday = requestedMonth.atDay(1);
		while (!currentday.isAfter(requestedMonth.atEndOfMonth())) {
			Collection<Booking> currentBookings = getBookingsForDay(currentday);
			if (hasCompleteBookings(currentBookings)) {
				String day = currentday.format(DateTimeFormatter.ISO_LOCAL_DATE);
				LocalTime starttime = Iterables.getFirst(currentBookings, null).getStarttime();
				LocalTime endtime = Iterables.getLast(currentBookings).getEndtime();
				Duration presence = calculatePresence(starttime, endtime);
				Duration worktime = calculateWorktime(currentBookings);
				Duration breaktime = calculateBreaktime(presence, worktime);
				Duration currentOvertime = calculateOvertime(worktime, currentday);
				overtime = overtime.plus(currentOvertime);
				boolean breaktimeOk = validateBreaktime(presence, breaktime);
				result.add(Arrays.asList(day, starttime.format(DateTimeFormatter.ofPattern("HH:mm")),
						endtime.format(DateTimeFormatter.ofPattern("HH:mm")),
						formatDuration(presence), formatDuration(worktime), getBreaktime(breaktime, breaktimeOk), formatDuration(overtime)));
			}
			currentday = currentday.plusDays(1);
		}
		return result;
	}

	private String getBreaktime(Duration breaktime, boolean breaktimeOk) {
		String appendix = breaktimeOk ? StringUtils.EMPTY : BREAKTIME_APPENDIX_NOT_VALID;
		return formatDuration(breaktime) + appendix;

	}

	private Collection<Booking> getBookingsForDay(final LocalDate currentday) {
		return store.retrieveAll().stream().filter(b -> b.getBookingday().equals(currentday))
				.sorted((b1, b2) -> b1.getStarttime().compareTo(b2.getStarttime())).collect(Collectors.toList());
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

	private Duration calculateBreaktime(final Duration presence, final Duration worktime) {
		return presence.minus(worktime);
	}

	private Duration calculateOvertime(final Duration worktime, final LocalDate day) {
		Duration minutes = worktime;
		if (isWeekDay(day)) {
			minutes = minutes.minus(Duration.ofMinutes(480)); // Overtime is time after 8 hours
		}
		return minutes;
	}

	private boolean isWeekDay(final LocalDate day) {
		return !(day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY);
	}

	private boolean validateBreaktime(final Duration presence, final Duration breaktime) {
		long minutes = presence.toMinutes();
		if (minutes >= 585) { // longer than 9,75 hours => 45 minutes break
			return breaktime.toMinutes() >= 45;
		} else if (minutes >= 570) { // longer than 9,5 hours => 9 hours worktime
			return breaktime.toMinutes() >= minutes - 540;
		} else if (minutes >= 390) { // longer than 6,5 hours => 30 minutes
			return breaktime.toMinutes() >= 30;
		} else if (minutes >= 360) { // longer than 6 hours => 6 hours worktime
			return breaktime.toMinutes() >= minutes - 360;
		}
		return true;
	}

	private Duration calculatePresence(final LocalTime starttime, final LocalTime endtime) {
		return Duration.between(starttime, endtime);
	}

	private Duration calculateWorktime(Collection<Booking> bookings) {
		Duration minutes = Duration.ZERO;
		for (Booking current : bookings) {
			minutes = minutes.plus(current.calculateTimeSpan().getLengthInMinutes());
		}
		return minutes;
	}

	private String formatDuration(final Duration duration) {
		long minutes = duration.toMinutes();
		char pre = minutes < 0 ? '-' : ' ';
		minutes = Math.abs(minutes);
		return String.format("%c%02d:%02d", pre, minutes / 60, minutes % 60);
	}

	public void setStore(final ObjectStore<Booking> store) {
		this.store = store;
	}
}
