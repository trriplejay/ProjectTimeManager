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
 * Run an analysis on the data
 */
@Parameters(commandDescription="Run an projects anaylsis for a monthor a day")
public class RunProjectAnalysis extends AbstractCommandHandler {
	private static final String ANALYSIS_PROJECTS_ID = "PROJECTS";

	@Parameter(names = { "-m", "--month" }, description="Month for the hour analysis", converter= YearMonthConverter.class)
	private YearMonth bookingMonth = YearMonth.now();

	@Parameter(names = { "-d", "--day" }, description="Day for project analysis", converter= LocalDateConverter.class)
	private LocalDate bookingDay = null;

	@Override
	public void handleCommand() {
		String parameter = bookingMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
		if (bookingDay != null) {
			parameter = bookingDay.format(DateTimeFormatter.ISO_LOCAL_DATE);
		}
		getLogger().log("Run analysis project analysis for " + parameter + " ...");
		Collection<Collection<Object>> result = getServices().getAnalysisService().analyze(ANALYSIS_PROJECTS_ID, Arrays.asList(parameter));
		getPrinter().tablePrint(result);
		getLogger().log("... analysis done");
	}
}
