/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

abstract class AbstractRunAnalysis extends AbstractCommandHandler {
    protected void runAnalysis(String command, YearMonth month, LocalDate week, LocalDate day) {
        String frametype = extractTimeFrameType(month, week, day);
        String timeframe = parseTimeFrame(month, week, day);
        getLogger().log("Run analysis "+ command.toLowerCase() + " analysis for " + timeframe + " ...");
        Collection<Collection<Object>> result = getServices().getAnalysisService().analyze(command, Arrays.asList(frametype, timeframe));
        getPrinter().tablePrint(result);
        getLogger().log("... analysis done");
    }

    private String parseTimeFrame(YearMonth month, LocalDate week, LocalDate day) {
        if (day != null) {
            return day.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        if (week != null) {
            return week.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        if (month != null) {
            return month.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }
        return YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    private String extractTimeFrameType(YearMonth month, LocalDate week, LocalDate day) {
        if (day != null) {
            return "day";
        }
        if (week != null) {
            return "week";
        }
        return "month";
    }
}
