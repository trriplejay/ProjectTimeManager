/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;

import com.google.common.base.Preconditions;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * The implementation of the DayBookings service
 */
public class BookingServiceImpl implements BookingService {
    @Override
    @NonNull
    public DayBookings createNewDayBookings(@NonNull final LocalDate day) {
	return DayBookings.newDay(day);
    }

    @SuppressWarnings("null")
    @Override
    @NonNull
    public Booking addBooking(@NonNull final DayBookings dayBookings, @NonNull final Activity activity) {
	checkState(isNotEmpty(dayBookings.getBookings()));
	Booking lastBooking = dayBookings.getLastBooking();
	checkState(lastBooking != null);
	checkState(lastBooking.hasEndtime());
	return addBooking(dayBookings, activity, lastBooking.getEndtime());
    }

    @Override
    @NonNull
    public Booking addBooking(@NonNull final DayBookings dayBookings, @NonNull final Activity activity,
	    @NonNull final LocalTime starttime) {
	Booking lastBooking = dayBookings.getLastBooking();
	if (lastBooking != null) {
	    endBooking(dayBookings, lastBooking, starttime);
	}
	Booking newBooking = Booking.newBooking().setStarttime(starttime).setActivity(activity).build();
	dayBookings.addBooking(newBooking);
	return newBooking;
    }

    @Override
    @NonNull
    public Booking endBooking(@NonNull final DayBookings dayBookings, @NonNull final Booking booking,
	    @NonNull final LocalTime endtime) {
	Preconditions.checkState(dayBookings.getBookings().contains(booking));
	Preconditions.checkState(!booking.hasEndtime() || booking.getEndtime().equals(endtime));
	Booking endedBooking = booking.changeBooking().setEndtime(endtime).build();
	dayBookings.replaceBooking(booking, endedBooking);
	return endedBooking;
    }

    @Override
    public void removeBooking(@NonNull final DayBookings dayBookings, @NonNull final Booking booking) {
	Preconditions.checkState(dayBookings.getBookings().contains(booking));
	final Booking previousBooking = getPreviousBooking(dayBookings, booking);
	dayBookings.removeBooking(booking);
	if (previousBooking != null) {
	    Booking newBooking = previousBooking.changeBooking().setEndtime(booking.getEndtime()).build();
	    dayBookings.replaceBooking(previousBooking, newBooking);
	}
    }

    @SuppressWarnings("null")
    private Booking getPreviousBooking(@NonNull final DayBookings dayBookings, @NonNull final Booking booking) {
	int index = dayBookings.getBookings().indexOf(booking);
	if (index > 0) {
	    return dayBookings.getBookings().get(index - 1);
	}
	return null;
    }

    @SuppressWarnings("null")
    private Booking getSuccessiveBooking(@NonNull final DayBookings dayBookings, @NonNull final Booking booking) {
	int index = dayBookings.getBookings().indexOf(booking);
	if (index < dayBookings.getBookings().size() - 1) {
	    return dayBookings.getBookings().get(index + 1);
	}
	return null;
    }

    @Override
    public @NonNull Booking splitBooking(@NonNull final DayBookings dayBookings, @NonNull final Booking booking,
	    @NonNull final LocalTime splittime) {
	Preconditions.checkState(dayBookings.getBookings().contains(booking));
	Booking first = booking.changeBooking().setEndtime(splittime).build();
	Booking second = booking.changeBooking().setStarttime(splittime).setEndtime(booking.getEndtime()).build();
	dayBookings.replaceBooking(booking, first);
	dayBookings.addBooking(second);
	return first;
    }

    @SuppressWarnings("null")
    @Override
    public @NonNull Booking changeBookingTimes(@NonNull final DayBookings dayBookings, @NonNull final Booking booking,
	    @NonNull final LocalTime starttime, @NonNull final LocalTime endtime) {
	Preconditions.checkState(dayBookings.getBookings().contains(booking));
	Booking newBooking = booking.changeBooking().setStarttime(starttime).setEndtime(endtime).build();
	Booking previousBooking = getPreviousBooking(dayBookings, booking);
	Booking newPrevious = previousBooking != null ? previousBooking.changeBooking().setEndtime(starttime).build()
		: null;
	Booking successiveBooking = getSuccessiveBooking(dayBookings, booking);
	Booking newSuccessive = successiveBooking != null ? successiveBooking.changeBooking().setStarttime(endtime)
		.setEndtime(successiveBooking.getEndtime()).build() : null;

	dayBookings.replaceBooking(booking, newBooking);
	if (newPrevious != null) {
	    dayBookings.replaceBooking(previousBooking, newPrevious);
	}
	if (newSuccessive != null) {
	    dayBookings.replaceBooking(successiveBooking, newSuccessive);
	}
	return newBooking;
    }

    @Override
    public @NonNull Booking changeActivity(@NonNull final DayBookings dayBookings, @NonNull final Booking booking,
	    @NonNull final Activity activity) {
	Preconditions.checkState(dayBookings.getBookings().contains(booking));
	Preconditions.checkState(!activity.equals(booking.getActivity()));
	Booking newBooking = booking.changeBooking().setActivity(activity).build();
	dayBookings.replaceBooking(booking, newBooking);
	return newBooking;
    }

    @Override
    public @NonNull Booking changeComment(@NonNull final DayBookings dayBookings, @NonNull final Booking booking,
	    @NonNull final String comment) {
	Preconditions.checkState(dayBookings.getBookings().contains(booking));
	Preconditions.checkState(StringUtils.isNotBlank(comment));
	Preconditions.checkState(!comment.equals(booking.getComment()));
	Booking newBooking = booking.changeBooking().setComment(comment).build();
	dayBookings.replaceBooking(booking, newBooking);
	return newBooking;
    }

    @Override
    public @NonNull Booking deleteComment(@NonNull final DayBookings dayBookings, @NonNull final Booking booking) {
	Preconditions.checkState(dayBookings.getBookings().contains(booking));
	Preconditions.checkState(StringUtils.isNotBlank(booking.getComment()));
	@SuppressWarnings("null")
	Booking newBooking = booking.changeBooking().setComment(StringUtils.EMPTY).build();
	dayBookings.replaceBooking(booking, newBooking);
	return newBooking;
    }
}
