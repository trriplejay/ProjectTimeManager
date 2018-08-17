/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.time.LocalDate;
import java.time.YearMonth;


/**
 * Run an analysis on the data
 */
@Parameters(commandDescription="Run an projects anaylsis for a monthor a day")
public class RunProjectAnalysis extends AbstractRunAnalysis {
	private static final String ANALYSIS_PROJECTS_ID = "PROJECTS";

	@Parameter(names = { "-m", "--month" }, description="Month for the hour analysis", converter= YearMonthConverter.class)
	private YearMonth bookingMonth = YearMonth.now();

	@Parameter(names = { "-w", "--week" }, description="Day in week for project analysis", converter= LocalDateConverter.class)
	private LocalDate bookingDayInWeek = null;

	@Parameter(names = { "-d", "--day" }, description="Day for project analysis", converter= LocalDateConverter.class)
	private LocalDate bookingDay = null;

	@Override
	public void handleCommand() {
		runAnalysis(ANALYSIS_PROJECTS_ID, bookingMonth, bookingDayInWeek, bookingDay);
	}
}
