/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.util.Optional;

/**
 * End a booking that has been started with start booking command
 */
@Parameters(commandDescription="Add an end time to an existing booking")
public class ChangeBooking extends AbstractCommandHandler {
	@Parameter(names = { "-b", "--booking" }, description="Booking id of the booking to end", required=true)
	private Long id;

	@Parameter(names = { "-a", "--activity" }, description="Activity id of the bookings activity")
	private Long activityId = -1L;

	@Parameter(names = { "-s", "--starttime" }, description="Start time of the booked time frame", converter= LocalTimeConverter.class)
	private Optional<LocalTime> starttime = Optional.empty();

	@Parameter(names = { "-e", "--endtime" }, description="End time of the booked time frame", converter=LocalTimeConverter.class)
	private Optional<LocalTime> endtime = Optional.empty();

	@Parameter(names = { "-c", "--comment" }, description="Comment on booked time frame")
	private String comment = StringUtils.EMPTY;

	@Override
	public void handleCommand() {
		getLogger().log("Change booking ...");
		getServices().getBookingsStore().retrieveById(id).ifPresent(b -> {
			Booking changed = getServices().getBookingService().changeBooking(b, Optional.empty(), getActivityById(activityId),
					starttime, endtime, StringUtils.isNotBlank(comment) ? Optional.of(comment) : Optional.empty());
			getLogger().log("... new booking data: " + changed.toString());
		});
	}

	private Optional<Activity> getActivityById(final Long id) {
		if (id >= 0) {
		    Optional<Activity> returnValue = getServices().getActivityStore().retrieveById(id);
		    if (returnValue.isPresent()) {
		        return returnValue;
            }
            throw new IllegalStateException("Activity not known");
		}
		else {
			return Optional.empty();
		}
	}
}
