/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;


/**
 * Run an hour analysis on the data
 */
@Parameters(commandDescription="Run an hour analysis for a month")
public class RunHourAnalysis extends AbstractCommandHandler {
	private static final String ANALYSIS_HOURS_ID = "HOURS";

	@Parameter(names = { "-m", "--month" }, description="Optional day for booking", converter= YearMonthConverter.class)
	private YearMonth bookingMonth = YearMonth.now();

	@Parameter(names = { "-w", "--week" }, description="Day in week for project analysis", converter= LocalDateConverter.class)
	private LocalDate bookingDayInWeek = null;

	@Override
	public void handleCommand() {
		String date = bookingMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
		String timeframe = "month";
		if (bookingDayInWeek != null) {
			date = bookingDayInWeek.format(DateTimeFormatter.ISO_LOCAL_DATE);
			timeframe = "week";
		}
		getLogger().log("Run analysis project analysis for month " + bookingMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")) + " ...");
		Collection<Collection<Object>> result = getServices().getAnalysisService().analyze(ANALYSIS_HOURS_ID,
				Arrays.asList(timeframe, date));
		getPrinter().tablePrint(result);
		getLogger().log("... analysis done");
	}
}
