/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;


/**
 * Run an hour analysis on the data
 */
@Parameters(commandDescription="Run an hour anaylsis for a month")
public class RunHourAnalysis extends AbstractCommandHandler {
	private static final String ANALYSIS_HOURS_ID = "HOURS";

	@Parameter(names = { "-m", "--month" }, description="Optional day for booking", converter=YearMonthConverter.class)
	private YearMonth bookingMonth = YearMonth.now();

	@Override
	public void handleCommand() {
		getLogger().log("Run analysis project analysis for month " + bookingMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")) + " ...");
		Collection<Collection<Object>> result = getServices().getAnalysisService().analyze(ANALYSIS_HOURS_ID,
				Arrays.asList(bookingMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"))));
		for (Collection<Object> current : result) {
			getLogger().log(createString(current));
		}
		getLogger().log("... analysis done");
	}

	private String createString(final Collection<Object> columns) {
		StringBuilder resultString = new StringBuilder();
		resultString.append("| ");
		for (Object current : columns) {
			resultString.append(current);
			resultString.append("\t | ");
		}
		return resultString.toString();
	}
}
