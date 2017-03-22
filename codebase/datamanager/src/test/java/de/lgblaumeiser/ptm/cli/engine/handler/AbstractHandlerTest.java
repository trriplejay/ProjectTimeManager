/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.collect.Lists.newArrayList;
import static de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler.setLogger;
import static de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler.setServices;
import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static de.lgblaumeiser.ptm.datamanager.model.Booking.newBooking;
import static de.lgblaumeiser.ptm.datamanager.model.DayBookings.newDay;
import static java.util.Arrays.asList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.junit.Before;

import de.lgblaumeiser.ptm.analysis.DataAnalysisService;
import de.lgblaumeiser.ptm.cli.engine.CommandLogger;
import de.lgblaumeiser.ptm.cli.engine.ServiceManager;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.ptm.datamanager.service.BookingServiceImpl;
import de.lgblaumeiser.ptm.store.ObjectStore;

public abstract class AbstractHandlerTest {
	protected static final LocalDate DATE1 = LocalDate.of(2016, 06, 24);
	protected static final LocalTime TIME1 = LocalTime.of(12, 34);
	protected static final LocalTime TIME2 = LocalTime.of(13, 57);
	protected static final String ACTIVITY1NAME = "Act1";
	protected static final String ACTIVITY1NUMBER = "0815";
	protected static final String ACTIVITY2NAME = "NewAct2";
	protected static final String ACTIVITY2NUMBER = "4711";
	protected static final Activity ACTIVITY1 = newActivity(ACTIVITY1NAME, ACTIVITY1NUMBER);
	protected static final Activity ACTIVITY2 = newActivity(ACTIVITY2NAME, ACTIVITY2NUMBER);
	protected static final Booking BOOKING1 = newBooking().setStarttime(TIME1).setEndtime(TIME2).setActivity(ACTIVITY1)
			.build();
	protected DayBookings testDay = newDay(DATE1);

	protected boolean activityStoreCalled = false;
	protected boolean bookingStoreCalled = false;

	protected static class TestCommandLogger implements CommandLogger {
		protected StringBuffer logMessages = new StringBuffer();

		@Override
		public void log(String message) {
			logMessages.append(message);
			logMessages.append("xxxnewlinexxx");
		}
	}

	protected TestCommandLogger logger = new TestCommandLogger();
	protected ServiceManager services = new ServiceManager();

	@Before
	public void before() {
		services.getStateStore().setCurrentDay(testDay);
		services.setActivityStore(new ObjectStore<Activity>() {
			@Override
			public Activity store(Activity object) {
				activityStoreCalled = true;
				return object;
			}

			@Override
			public Collection<Activity> retrieveAll() {
				return asList(ACTIVITY1, ACTIVITY2);
			}
		});
		services.setBookingsStore(new ObjectStore<DayBookings>() {
			@Override
			public DayBookings store(DayBookings object) {
				bookingStoreCalled = true;
				return object;
			}

			@Override
			public Collection<DayBookings> retrieveAll() {
				return asList(testDay);
			}
		});
		services.setBookingService(new BookingServiceImpl());
		services.setAnalysisService(new DataAnalysisService() {
			@Override
			public Collection<Collection<Object>> analyze(String analyzerId, Collection<String> parameter) {
				Collection<Object> firstParam = asList(analyzerId);
				Collection<Object> secondParam = newArrayList(parameter);
				Collection<Collection<Object>> returnlist = newArrayList();
				returnlist.add(firstParam);
				returnlist.add(secondParam);
				return returnlist;
			}
		});
		setLogger(logger);
		setServices(services);
	}
}
