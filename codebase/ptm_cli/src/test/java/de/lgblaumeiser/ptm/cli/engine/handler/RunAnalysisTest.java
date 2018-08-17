/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import org.junit.Test;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class RunAnalysisTest extends AbstractHandlerTest {
    private static final String HOURS_ANALYSIS_COMMAND = "hour_analysis";
    private static final String ANALYSIS_HOURS_ID = "HOURS";
    private static final String PROJECTS_ANALYSIS_COMMAND = "project_analysis";
    private static final String ANALYSIS_PROJECTS_ID = "PROJECTS";

    private static final String DATE_FOR_ANALYSIS = "2018-04-05";
    private static final String MONTH_FOR_ANALYSIS = "2018-04";

    @Test
	public void testRunHoursAnalysisThisMonth() {
        commandline.runCommand(HOURS_ANALYSIS_COMMAND);
        assertEquals("/analysis/" + ANALYSIS_HOURS_ID + "/month/" + YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM")), restutils.apiNameGiven);
	}

    @Test
    public void testRunHoursAnalysisGivenMonth() {
        commandline.runCommand(HOURS_ANALYSIS_COMMAND, "-m", MONTH_FOR_ANALYSIS);
        assertEquals("/analysis/" + ANALYSIS_HOURS_ID + "/month/" + MONTH_FOR_ANALYSIS, restutils.apiNameGiven);
    }

    @Test
    public void testRunHoursAnalysisGivenWeekday() {
        commandline.runCommand(HOURS_ANALYSIS_COMMAND, "-w", DATE_FOR_ANALYSIS);
        assertEquals("/analysis/" + ANALYSIS_HOURS_ID + "/week/" + DATE_FOR_ANALYSIS, restutils.apiNameGiven);
    }

    @Test
    public void testRunProjectsAnalysisThisMonth() {
        commandline.runCommand(PROJECTS_ANALYSIS_COMMAND);
        assertEquals("/analysis/" + ANALYSIS_PROJECTS_ID + "/month/" + YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM")), restutils.apiNameGiven);
    }

    @Test
    public void testRunProjectsAnalysisGivenMonth() {
        commandline.runCommand(PROJECTS_ANALYSIS_COMMAND, "-m", MONTH_FOR_ANALYSIS);
        assertEquals("/analysis/" + ANALYSIS_PROJECTS_ID + "/month/" + MONTH_FOR_ANALYSIS, restutils.apiNameGiven);
    }

    @Test
    public void testRunProjectsAnalysisGivenDay() {
        commandline.runCommand(PROJECTS_ANALYSIS_COMMAND, "-d", DATE_FOR_ANALYSIS);
        assertEquals("/analysis/" + ANALYSIS_PROJECTS_ID + "/day/" + DATE_FOR_ANALYSIS, restutils.apiNameGiven);
    }

    @Test
    public void testRunProjectsAnalysisGivenWeek() {
        commandline.runCommand(PROJECTS_ANALYSIS_COMMAND, "-w", DATE_FOR_ANALYSIS);
        assertEquals("/analysis/" + ANALYSIS_PROJECTS_ID + "/week/" + DATE_FOR_ANALYSIS, restutils.apiNameGiven);
    }
}
