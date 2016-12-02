/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * The implementation of the DayBookings service
 */
public class BookingServiceImpl implements BookingService {
	@Override
	public DayBookings createNewDayBookings(final LocalDate day) {
		checkNotNull(day);
		return DayBookings.newDay(day);
	}

	@Override
	public Booking addBooking(final DayBookings dayBookings, final Activity activity) {
		checkNotNull(dayBookings);
		checkNotNull(activity);
		checkState(isNotEmpty(dayBookings.getBookings()));
		Booking lastBooking = dayBookings.getLastBooking();
		checkState(lastBooking != null);
		checkState(lastBooking.hasEndtime());
		return addBooking(dayBookings, activity, lastBooking.getEndtime());
	}

	@Override
	public Booking addBooking(final DayBookings dayBookings, final Activity activity, final LocalTime starttime) {
		checkNotNull(dayBookings);
		checkNotNull(activity);
		checkNotNull(starttime);
		Booking lastBooking = dayBookings.getLastBooking();
		if (lastBooking != null && !lastBooking.hasEndtime()) {
			endBooking(dayBookings, lastBooking, starttime);
		}
		Booking newBooking = Booking.newBooking().setStarttime(starttime).setActivity(activity).build();
		dayBookings.addBooking(newBooking);
		return newBooking;
	}

	@Override
	public Booking endBooking(final DayBookings dayBookings, final Booking booking, final LocalTime endtime) {
		checkNotNull(dayBookings);
		checkNotNull(booking);
		checkNotNull(endtime);
		checkState(dayBookings.getBookings().contains(booking));
		checkState(booking.getEndtime().equals(endtime));
		Booking endedBooking = booking.changeBooking().setEndtime(endtime).build();
		dayBookings.replaceBooking(booking, endedBooking);
		return endedBooking;
	}

	@Override
	public void removeBooking(final DayBookings dayBookings, final Booking booking) {
		checkNotNull(dayBookings);
		checkNotNull(booking);
		checkState(dayBookings.getBookings().contains(booking));
		dayBookings.removeBooking(booking);
	}
}
