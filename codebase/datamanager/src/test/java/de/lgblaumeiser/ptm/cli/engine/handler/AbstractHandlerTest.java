package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.util.Arrays.asList;
import static com.google.common.collect.Lists.newArrayList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Properties;

import org.junit.Before;

import de.lgblaumeiser.ptm.analysis.DataAnalysisService;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.cli.engine.CommandLogger;
import de.lgblaumeiser.ptm.cli.engine.ServiceManager;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.ActivityModel;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.ptm.datamanager.service.ActivityServiceImpl;
import de.lgblaumeiser.ptm.datamanager.service.BookingServiceImpl;
import de.lgblaumeiser.store.ObjectStore;

public abstract class AbstractHandlerTest {
	protected static final LocalDate DATE1 = LocalDate.of(2016, 06, 24);
	protected static final LocalTime TIME1 = LocalTime.of(12, 34);
	protected static final LocalTime TIME2 = LocalTime.of(13, 57);
	protected static final String ACTIVITY1NAME = "Act1";
	protected static final String ACTIVITY1NUMBER = "0815";
	protected static final Activity ACTIVITY1 = Activity.newProjectActivity(ACTIVITY1NAME, ACTIVITY1NUMBER);
	protected static final Activity ACTIVITY2 = Activity.newLineActivity(ACTIVITY1NAME, ACTIVITY1NUMBER);
	protected static final Booking BOOKING1 = Booking.newBooking().setStarttime(TIME1).setEndtime(TIME2)
			.setActivity(ACTIVITY1).build();
	protected DayBookings testDay = DayBookings.newDay(DATE1);
	protected ActivityModel actModel = new ActivityModel();

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
		services.setActivityStore(new ObjectStore<ActivityModel>() {
			@Override
			public void configure(Properties properties) {
				// pure dummy, do not do anything
			}

			@Override
			public void store(ActivityModel object) {
				activityStoreCalled = true;
			}

			@Override
			public ActivityModel retrieveByIndexKey(Object key) {
				return actModel;
			}
		});
		services.setBookingsStore(new ObjectStore<DayBookings>() {
			@Override
			public void configure(Properties properties) {
				// pure dummy, do not do anything
			}

			@Override
			public void store(DayBookings object) {
				bookingStoreCalled = true;
			}

			@Override
			public DayBookings retrieveByIndexKey(Object key) {
				if (key.equals(services.getStateStore().getCurrentDay().getDay())) {
					return services.getStateStore().getCurrentDay();
				}
				return null;
			}			
		});
		ActivityServiceImpl activityService = new ActivityServiceImpl();
		activityService.setActivityStore(actModel);
		services.setActivityService(activityService);
		services.setBookingService(new BookingServiceImpl());
		services.setAnalysisService(new DataAnalysisService () {
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
		AbstractCommandHandler.setLogger(logger);
		AbstractCommandHandler.setServices(services);
	}
}
