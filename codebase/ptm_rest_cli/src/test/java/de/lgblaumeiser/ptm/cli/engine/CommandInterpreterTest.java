/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class CommandInterpreterTest {
	private CommandInterpreter testee;

	private static final String COMMAND1 = "G";
	private static final String COMMAND2 = "B";
	private static final String COMMAND3 = "1";
	private static final String COMMAND4 = "$";
	private static final String COMMAND5 = "T";

	private static final String PARAM1 = "5:4:3";
	private static final String PARAM2 = "act1";

	private static boolean called = false;

	private static final CommandHandler TESTHANDLER1 = new CommandHandler() {
		@Override
		public void handleCommand(final Collection<String> parameters) {
			assertTrue(parameters.isEmpty());
			called = true;
		}
	};

	private static final CommandHandler TESTHANDLER2 = new CommandHandler() {
		@Override
		public void handleCommand(final Collection<String> parameters) {
			assertEquals(2, parameters.size());
			parameters.stream().allMatch(s -> s.equals(PARAM1) || s.equals(PARAM2));
			called = true;
		}
	};

	@Before
	public void setUp() throws Exception {
		testee = new CommandInterpreter();
		testee.addCommandHandler(COMMAND1.toLowerCase(), TESTHANDLER1);
		testee.addCommandHandler(COMMAND2, TESTHANDLER2);
	}

	@Test
	public void testHandlePositiveNoParam() {
		called = false;
		testee.handle(COMMAND1 + " ");
		assertTrue(called);
	}

	@Test
	public void testHandlePositiveTwoParam() {
		called = false;
		testee.handle(COMMAND2 + " " + PARAM1 + " " + PARAM2);
		assertTrue(called);
	}

	@Test(expected = IllegalStateException.class)
	public void testHandleNegativeNoSpaces() {
		testee.handle(COMMAND2 + PARAM1 + PARAM2);
	}

	@Test(expected = IllegalStateException.class)
	public void testHandleNegativeWrongCommand3() {
		testee.handle(COMMAND3);
	}

	@Test(expected = IllegalStateException.class)
	public void testHandleNegativeWrongCommand4() {
		testee.handle(COMMAND4);
	}

	@Test(expected = IllegalStateException.class)
	public void testHandleNegativeWrongCommand5() {
		testee.handle(COMMAND5);
	}
}
