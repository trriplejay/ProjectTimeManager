/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import com.google.common.collect.Iterables;
import de.lgblaumeiser.ptm.analysis.Analysis;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;

public abstract class AbstractBaseComputer implements Analysis {
    protected ObjectStore<Booking> store;

    static class CalculationPeriod {
        LocalDate firstDay;
        LocalDate firstDayAfter;

        public CalculationPeriod(LocalDate firstDay, LocalDate firstDayAfter) {
            this.firstDay = firstDay;
            this.firstDayAfter = firstDayAfter;
        }
    }

    protected CalculationPeriod getCalculationPeriod(Collection<String> parameter) {
        String selector = Iterables.get(parameter, 0);
        String time = Iterables.get(parameter, 1);
        if ("day".equals(selector.toLowerCase())) {
            return getDayPeriod(LocalDate.parse(time));
        }
        else if ("week".equals(selector.toLowerCase())) {
            return getWeekPeriod(LocalDate.parse(time));
        }
        else if ("month".equals(selector.toLowerCase())) {
            return getMonthPeriod(YearMonth.parse(time));
        }
        return getMonthPeriod(YearMonth.now());
    }

    protected CalculationPeriod getDayPeriod(LocalDate day) {
        return new CalculationPeriod(day, day.plusDays(1L));
    }

    private CalculationPeriod getWeekPeriod(LocalDate dayInWeek) {
        LocalDate current = dayInWeek;
        while (current.getDayOfWeek() != DayOfWeek.MONDAY) {
            current = current.minusDays(1L);
        }
        return new CalculationPeriod(current, current.plusDays(7L));
    }

    protected CalculationPeriod getMonthPeriod(YearMonth month) {
        return new CalculationPeriod(month.atDay(1), month.plusMonths(1L).atDay(1));
    }

    protected boolean isInPeriod(CalculationPeriod period, LocalDate current) {
        return period.firstDay.minusDays(1L).isBefore(current) && period.firstDayAfter.isAfter(current);
    }

    public void setStore(final ObjectStore<Booking> store) {
        this.store = store;
    }
}
