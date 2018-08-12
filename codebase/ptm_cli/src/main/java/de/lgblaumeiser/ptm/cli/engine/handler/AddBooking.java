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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Add a booking for the day
 */
@Parameters(commandDescription="Add a new booking")
public class AddBooking extends AbstractCommandHandler {
	@Parameter(names = { "-d", "--day" }, description="Optional day for booking", converter= LocalDateConverter.class)
	private LocalDate bookingDay = LocalDate.now();

	@Parameter(names = { "-a", "--activity" }, description="Activity id of the bookings activity", required=true)
	private Long activityId;

	@Parameter(names = { "-s", "--starttime" }, description="Start time of the booked time frame", required=true, converter= LocalTimeConverter.class)
	private Optional<LocalTime> starttime;

	@Parameter(names = { "-e", "--endtime" }, description="End time of the booked time frame", converter=LocalTimeConverter.class)
	private Optional<LocalTime> endtime = Optional.empty();

	@Parameter(names = { "-u", "--user" }, description="User for which time frame is booked", required=true)
	private String user;

	@Parameter(names = { "-c", "--comment" }, description="Comment on booked time frame")
	private String comment = StringUtils.EMPTY;

	@Override
	public void handleCommand() {
		getLogger().log("Add new booking ...");
		Booking addedBooking = getServices().getBookingService().addBooking(bookingDay, user, getActivityById(activityId), starttime.get(), comment);
		if (endtime.isPresent()) {
			addedBooking = getServices().getBookingService().endBooking(addedBooking, endtime.get());
		}
		getLogger().log(" ... booking added with data: " + addedBooking.toString());
	}

	private Activity getActivityById(final Long id) {
		return getServices().getActivityStore().retrieveById(id).orElseThrow(IllegalStateException::new);
	}
}