/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import de.lgblaumeiser.ptm.cli.CLI;
import de.lgblaumeiser.ptm.cli.PTMCLIConfigurator;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.cli.engine.CommandLogger;
import de.lgblaumeiser.ptm.cli.rest.RestBaseService;
import de.lgblaumeiser.ptm.cli.rest.RestUtils;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import org.junit.Before;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;

public abstract class AbstractHandlerTest {
	protected static final LocalDate DATE1 = LocalDate.of(2016, 06, 24);
	protected static final LocalTime TIME1 = LocalTime.of(12, 34);
	protected static final LocalTime TIME2 = LocalTime.of(13, 57);
	protected static final String ACTIVITY1NAME = "Act1";
	protected static final String ACTIVITY1NUMBER = "0815";
	protected static final String ACTIVITY2NAME = "NewAct2";
	protected static final String ACTIVITY2NUMBER = "4711";
	protected static final Activity ACTIVITY1 = newActivity().setActivityName(ACTIVITY1NAME).setBookingNumber(ACTIVITY1NUMBER).setId(1L).build();
	protected static final Activity ACTIVITY2 = newActivity().setActivityName(ACTIVITY2NAME).setBookingNumber(ACTIVITY2NUMBER).setId(2L).build();
	protected static final String USER = "TestUser";
	protected static final String COMMENT = "TestComment";

	protected static class TestCommandLogger implements CommandLogger {
		protected StringBuffer logMessages = new StringBuffer();

		@Override
		public void log(String message) {
			logMessages.append(message);
			logMessages.append("xxxnewlinexxx");
		}
	}

	protected static class TestRestUtils extends RestUtils {
	    protected String apiNameGiven;
	    protected Map<String, String> bodyDataGiven;

        @Override
        public Long post(String apiName, Map<String, String> bodyData) {
            apiNameGiven = apiName;
            bodyDataGiven = bodyData;
            return 2L;
        }

        @Override
        public <T> T get(String apiName, Class<T> returnClass) {
        	apiNameGiven = apiName;
        	if (apiName.contains("activities")) {
        		if (apiName.contains("1")) {
        			return returnClass.cast(ACTIVITY1);
				}
				else if (apiName.contains("2")) {
					return returnClass.cast(ACTIVITY2);
				}
			}
			if (returnClass.isArray()) {
				return returnClass.cast(Array.newInstance(returnClass.getComponentType(), 0));
			}
			return null;
        }

        @Override
        public void delete(String apiName) {
        	apiNameGiven = apiName;
        }

		@Override
		public TestRestUtils configure() {
			return this;
		}
	}

	protected TestCommandLogger logger = new TestCommandLogger();
	protected TestRestUtils restutils = new TestRestUtils().configure();
	protected CLI commandline = new PTMCLIConfigurator().configure();

	@Before
	public void before() {
        AbstractCommandHandler.setLogger(logger);
        RestBaseService.setRestUtils(restutils);
	}
}
