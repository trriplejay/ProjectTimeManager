/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.lgblaumeiser.ptm.analysis.Analysis;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * An analysis that counts all hours in the month given as parameter
 */
public class HourComputer implements Analysis {
	private static final String BREAKTIME_COMMENT = "Break too short!";
	private static final String WORKTIME_COMMENT = "> 10 hours worktime!";
	private ObjectStore<Booking> store;

	@Override
	public Collection<Collection<Object>> analyze(final Collection<String> parameter) {
		YearMonth requestedMonth = YearMonth.now();
		if (parameter.size() > 0) {
			requestedMonth = YearMonth.parse(Iterables.get(parameter, 0));
		}
		Collection<Collection<Object>> result = Lists.newArrayList();
		result.add(Arrays.asList("Work Day", "Starttime", "Endtime", "Presence", "Worktime", "Breaktime", "Total", "Overtime", "Comment"));
		Duration overtime = Duration.ZERO;
		Duration totaltime = Duration.ZERO;
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
				totaltime = totaltime.plus(worktime);
				Duration currentOvertime = calculateOvertime(worktime, currentday);
				overtime = overtime.plus(currentOvertime);
				result.add(Arrays.asList(day, starttime.format(DateTimeFormatter.ofPattern("HH:mm")),
						endtime.format(DateTimeFormatter.ofPattern("HH:mm")),
						formatDuration(presence), formatDuration(worktime), formatDuration(breaktime),
						formatDuration(totaltime), formatDuration(overtime), validate(worktime, breaktime)));
			}
			currentday = currentday.plusDays(1);
		}
		return result;
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

	private String validate(final Duration worktime, final Duration breaktime) {
		long worktimeMinutes = worktime.toMinutes();
		long breaktimeMinutes = breaktime.toMinutes();
		if (worktimeMinutes > 600) {
			return WORKTIME_COMMENT;
		}
		if (worktimeMinutes > 540 && breaktimeMinutes < 45) { // longer than 9 hours => 45 minutes break
			return BREAKTIME_COMMENT;
		}
		if (worktimeMinutes > 360 && breaktimeMinutes < 30) { // longer than 6 hours => 30 minutes break
			return BREAKTIME_COMMENT;
		}
		return StringUtils.EMPTY;
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
