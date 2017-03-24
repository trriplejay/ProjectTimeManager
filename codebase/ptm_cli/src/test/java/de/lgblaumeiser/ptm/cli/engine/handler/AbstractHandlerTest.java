/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.collect.Lists.newArrayList;
import static de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler.setLogger;
import static de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler.setServices;
import static de.lgblaumeiser.ptm.datamanager.model.Activity.newActivity;
import static java.util.Arrays.asList;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;

import de.lgblaumeiser.ptm.analysis.DataAnalysisService;
import de.lgblaumeiser.ptm.cli.engine.CommandLogger;
import de.lgblaumeiser.ptm.cli.engine.ServiceManager;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
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
		services.getStateStore().setCurrentDay(DATE1);
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

			@Override
			public Optional<Activity> retrieveById(Long id) {
				if (id == 1) {
					return Optional.of(ACTIVITY1);
				} else if (id == 2) {
					return Optional.of(ACTIVITY2);
				}
				return Optional.empty();
			}

			@Override
			public void deleteById(Long id) {
				// Not needed in tests
			}
		});
		services.setBookingsStore(new ObjectStore<Booking>() {
			private Collection<Booking> bookings = newArrayList();
			private Long id = 1L;

			@Override
			public Booking store(Booking object) {
				bookings.stream().filter(b -> b.getId() == object.getId()).findFirst().ifPresent(bookings::remove);
				try {
					Field f = object.getClass().getDeclaredField("id");
					f.setAccessible(true);
					f.set(object, id);
					id++;
					f.setAccessible(false);
				} catch (IllegalAccessException | IllegalArgumentException | ClassCastException | NoSuchFieldException
						| SecurityException e) {
					throw new IllegalStateException(e);
				}
				bookings.add(object);
				return object;
			}

			@Override
			public Collection<Booking> retrieveAll() {
				return bookings;
			}

			@Override
			public Optional<Booking> retrieveById(Long id) {
				return bookings.stream().filter(b -> b.getId() == id).findFirst();
			}

			@Override
			public void deleteById(Long id) {
				bookings.stream().filter(b -> b.getId() == id).findFirst().ifPresent(bookings::remove);
			}
		});
		services.setBookingService(new BookingServiceImpl().setBookingStore(services.getBookingsStore()));
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
