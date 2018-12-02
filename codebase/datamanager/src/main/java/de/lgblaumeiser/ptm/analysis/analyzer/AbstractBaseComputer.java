/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import static de.lgblaumeiser.ptm.util.Utils.getIndexFromCollection;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;

import de.lgblaumeiser.ptm.analysis.Analysis;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

public abstract class AbstractBaseComputer implements Analysis {
	protected final ObjectStore<Booking> store;

	static class CalculationPeriod {
		LocalDate firstDay;
		LocalDate firstDayAfter;
		boolean isDayPeriod;

		public CalculationPeriod(final LocalDate firstDay, final LocalDate firstDayAfter) {
			this.firstDay = firstDay;
			this.firstDayAfter = firstDayAfter;
			isDayPeriod = firstDay.equals(firstDayAfter.minusDays(1));
		}
	}

	protected CalculationPeriod getCalculationPeriod(final Collection<String> parameter) {
		String selector = getIndexFromCollection(parameter, 0);
		String time = getIndexFromCollection(parameter, 1);
		if ("day".equals(selector.toLowerCase())) {
			return getDayPeriod(LocalDate.parse(time));
		} else if ("week".equals(selector.toLowerCase())) {
			return getWeekPeriod(LocalDate.parse(time));
		} else if ("month".equals(selector.toLowerCase())) {
			return getMonthPeriod(YearMonth.parse(time));
		}
		return getMonthPeriod(YearMonth.now());
	}

	protected CalculationPeriod getDayPeriod(final LocalDate day) {
		return new CalculationPeriod(day, day.plusDays(1L));
	}

	private CalculationPeriod getWeekPeriod(final LocalDate dayInWeek) {
		LocalDate current = dayInWeek;
		while (current.getDayOfWeek() != DayOfWeek.MONDAY) {
			current = current.minusDays(1L);
		}
		return new CalculationPeriod(current, current.plusDays(7L));
	}

	protected CalculationPeriod getMonthPeriod(final YearMonth month) {
		return new CalculationPeriod(month.atDay(1), month.plusMonths(1L).atDay(1));
	}

	protected boolean isInPeriod(final CalculationPeriod period, final LocalDate current) {
		return period.firstDay.minusDays(1L).isBefore(current) && period.firstDayAfter.isAfter(current);
	}

	/**
	 * @param store The store with bookings used for analysis
	 */
	public AbstractBaseComputer(final ObjectStore<Booking> store) {
		this.store = store;
	}
}
