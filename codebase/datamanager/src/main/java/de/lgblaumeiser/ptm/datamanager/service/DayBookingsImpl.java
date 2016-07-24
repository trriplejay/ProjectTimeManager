/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * The implementation of the DayBookings service
 */
class DayBookingsImpl implements DayBookings {
    private final @NonNull LocalDate day;
    private final SortedSet<Booking> bookings = Sets.newTreeSet((A, B) -> A.getStarttime().compareTo(B.getStarttime()));

    /**
     * Create a DayBooking service implementation
     *
     * @param day
     *            The day for which bookings are stored.
     */
    DayBookingsImpl(@NonNull final LocalDate day) {
	this.day = day;
    }

    @SuppressWarnings("null")
    @Override
    @NonNull
    public Collection<@NonNull Booking> getBookings() {
	return Collections.unmodifiableCollection(bookings);
    }

    @Override
    public Booking getLastBooking() {
	return Iterables.getLast(bookings, null);
    }

    @Override
    @NonNull
    public LocalDate getDay() {
	return day;
    }

    @SuppressWarnings("null")
    @Override
    @NonNull
    public Booking addBooking(@NonNull final Activity activity) {
	Preconditions.checkState(CollectionUtils.isNotEmpty(bookings));
	Booking lastBooking = getLastBooking();
	Preconditions.checkState(lastBooking.hasEndtime());
	return addBooking(activity, lastBooking.getEndtime());
    }

    @Override
    @NonNull
    public Booking addBooking(@NonNull final Activity activity, @NonNull final LocalTime starttime) {
	Booking lastBooking = getLastBooking();
	if (lastBooking != null) {
	    endBooking(lastBooking, starttime);
	}
	Booking newBooking = Booking.newBooking().setStarttime(starttime).setActivity(activity).build();
	bookings.add(newBooking);
	return newBooking;
    }

    @Override
    @NonNull
    public Booking endBooking(@NonNull final Booking booking, @NonNull final LocalTime endtime) {
	Preconditions.checkState(bookings.contains(booking));
	Preconditions.checkState(!booking.hasEndtime() || booking.getEndtime().equals(endtime));
	Booking endedBooking = booking.changeBooking().setEndtime(endtime).build();
	bookings.remove(booking);
	bookings.add(endedBooking);
	return endedBooking;
    }

    @Override
    public void removeBooking(@NonNull final Booking booking) {
	Preconditions.checkState(bookings.contains(booking));
	final Booking previousBooking = getPreviousBooking(booking);
	bookings.remove(booking);
	if (previousBooking != null) {
	    Booking newBooking = previousBooking.changeBooking().setEndtime(booking.getEndtime()).build();
	    bookings.remove(previousBooking);
	    bookings.add(newBooking);
	}
    }

    private Booking getPreviousBooking(@NonNull final Booking booking) {
	Set<Booking> previousElements = bookings.headSet(booking);
	if (previousElements.isEmpty()) {
	    return null;
	}
	return Iterables.getLast(previousElements);
    }

    private Booking getSuccessiveBooking(@NonNull final Booking booking) {
	Set<Booking> successiveElements = bookings.tailSet(booking);
	if (successiveElements.size() == 1) {
	    return null;
	}
	Iterator<Booking> iter = successiveElements.iterator();
	iter.next();
	return iter.next();
    }

    @Override
    public @NonNull Booking splitBooking(@NonNull final Booking booking, @NonNull final LocalTime splittime) {
	Preconditions.checkState(bookings.contains(booking));
	Booking first = booking.changeBooking().setEndtime(splittime).build();
	Booking second = booking.changeBooking().setStarttime(splittime).setEndtime(booking.getEndtime()).build();
	bookings.remove(booking);
	bookings.add(first);
	bookings.add(second);
	return first;
    }

    @Override
    public @NonNull Booking changeBookingTimes(@NonNull final Booking booking, @NonNull final LocalTime starttime,
	    @NonNull final LocalTime endtime) {
	Preconditions.checkState(bookings.contains(booking));
	Booking newBooking = booking.changeBooking().setStarttime(starttime).setEndtime(endtime).build();
	Booking previousBooking = getPreviousBooking(booking);
	Booking newPrevious = previousBooking != null ? previousBooking.changeBooking().setEndtime(starttime).build()
		: null;
	Booking successiveBooking = getSuccessiveBooking(booking);
	Booking newSuccessive = successiveBooking != null ? successiveBooking.changeBooking().setStarttime(endtime)
		.setEndtime(successiveBooking.getEndtime()).build() : null;

	bookings.remove(booking);
	bookings.add(newBooking);
	if (newPrevious != null) {
	    bookings.remove(previousBooking);
	    bookings.add(newPrevious);
	}
	if (newSuccessive != null) {
	    bookings.remove(successiveBooking);
	    bookings.add(newSuccessive);
	}
	return newBooking;
    }

    @Override
    public @NonNull Booking changeActivity(@NonNull final Booking booking, @NonNull final Activity activity) {
	Preconditions.checkState(bookings.contains(booking));
	Preconditions.checkState(!activity.equals(booking.getActivity()));
	Booking newBooking = booking.changeBooking().setActivity(activity).build();
	bookings.remove(booking);
	bookings.add(newBooking);
	return newBooking;
    }

    @Override
    public @NonNull Booking changeComment(@NonNull final Booking booking, @NonNull final String comment) {
	Preconditions.checkState(bookings.contains(booking));
	Preconditions.checkState(StringUtils.isNotBlank(comment));
	Preconditions.checkState(!comment.equals(booking.getComment()));
	Booking newBooking = booking.changeBooking().setComment(comment).build();
	bookings.remove(booking);
	bookings.add(newBooking);
	return newBooking;
    }

    @Override
    public @NonNull Booking deleteComment(@NonNull final Booking booking) {
	Preconditions.checkState(bookings.contains(booking));
	Preconditions.checkState(StringUtils.isNotBlank(booking.getComment()));
	@SuppressWarnings("null")
	Booking newBooking = booking.changeBooking().setComment(StringUtils.EMPTY).build();
	bookings.remove(booking);
	bookings.add(newBooking);
	return newBooking;
    }

}
