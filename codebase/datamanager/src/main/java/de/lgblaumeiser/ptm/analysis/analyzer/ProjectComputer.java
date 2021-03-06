/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.TimeSpan;
import de.lgblaumeiser.ptm.store.ObjectStore;
import de.lgblaumeiser.ptm.util.Utils;

/**
 * An analysis to compute the amount of hours per a activity. The computer
 * calculates the percentage of an activity on the overall hours and maps these
 * percentages to the amount of 8 hours per booking day. This way, this fulfills
 * the requirements of the author concerning his time keeping.
 */
public class ProjectComputer extends AbstractBaseComputer {
	private ObjectStore<Activity> activityStore;

	@Override
	public Collection<Collection<Object>> analyze(final Collection<String> parameter) {
		Collection<Collection<Object>> result = new ArrayList<>();
		Duration totalMinutes = Duration.ZERO;
		Map<Long, Duration> activityToMinutesMap = new HashMap<>();
		Map<Long, String> activityToCommentMap = new HashMap<>();
		CalculationPeriod calcPeriod = getCalculationPeriod(parameter);
		for (Booking current : getBookingsForPeriod(calcPeriod)) {
			if (current.hasEndtime()) {
				Long currentActivity = current.getActivity();
				Duration accumulatedMinutes = activityToMinutesMap.get(currentActivity);
				if (accumulatedMinutes == null) {
					accumulatedMinutes = Duration.ZERO;
				}
				Duration activityLength = TimeSpan.newTimeSpan(current).getLengthInMinutes();
				totalMinutes = totalMinutes.plus(activityLength);
				accumulatedMinutes = accumulatedMinutes.plus(activityLength);
				activityToMinutesMap.put(currentActivity, accumulatedMinutes);
				if (calcPeriod.isDayPeriod) {
					String currentComments = activityToCommentMap.get(currentActivity);
					if (!Utils.stringHasContent(currentComments)) {
						currentComments = current.getComment();
					} else if (Utils.stringHasContent(current.getComment())) {
						currentComments = currentComments + ", " + current.getComment();
					}
					activityToCommentMap.put(currentActivity, currentComments);
				}
			}
		}
		if (calcPeriod.isDayPeriod) {
			result.add(Arrays.asList("Activity", "Booking number", "Hours", "%", "Comments"));
		} else {
			result.add(Arrays.asList("Activity", "Booking number", "Hours", "%"));
		}
		result.addAll(computeResultLines(totalMinutes, activityToMinutesMap, activityToCommentMap));
		if (calcPeriod.isDayPeriod) {
			result.add(Arrays.asList("Total", "", formatDuration(totalMinutes), "100.0%", ""));
		} else {
			result.add(Arrays.asList("Total", "", formatDuration(totalMinutes), "100.0%"));
		}
		return result;
	}

	private Collection<Collection<Object>> computeResultLines(Duration totalMinutes,
			Map<Long, Duration> activityToMinutesMap, Map<Long, String> activityToCommentsMap) {
		Collection<Collection<Object>> valueList = new ArrayList<>();
		for (Entry<Long, Duration> currentActivity : activityToMinutesMap.entrySet()) {
			Long activityId = currentActivity.getKey();
			Duration totalMinutesId = currentActivity.getValue();
			double percentage = (double) totalMinutesId.toMinutes() / (double) totalMinutes.toMinutes();
			String percentageString = String.format("%2.1f", percentage * 100.0) + "%";
			Activity activity = activityStore.retrieveById(activityId).orElseThrow(IllegalStateException::new);
			String comments = activityToCommentsMap.get(activityId);
			if (comments == null) {
				valueList.add(Arrays.asList(activity.getActivityName(), activity.getBookingNumber(),
						formatDuration(totalMinutesId), percentageString));
			} else {
				valueList.add(Arrays.asList(activity.getActivityName(), activity.getBookingNumber(),
						formatDuration(totalMinutesId), percentageString, comments));
			}
		}
		return valueList.stream()
				.sorted((line1, line2) -> Utils.getIndexFromCollection(line1, 1).toString()
						.compareToIgnoreCase(Utils.getIndexFromCollection(line2, 1).toString()))
				.collect(Collectors.toList());
	}

	private Collection<Booking> getBookingsForPeriod(final CalculationPeriod period) {
		return store.retrieveAll().stream().filter(b -> isInPeriod(period, b.getBookingday()))
				.collect(Collectors.toList());
	}

	private String formatDuration(final Duration duration) {
		long minutes = duration.toMinutes();
		char pre = minutes < 0 ? '-' : ' ';
		minutes = Math.abs(minutes);
		return String.format("%c%02d:%02d", pre, minutes / 60, minutes % 60);
	}

	public ProjectComputer(final ObjectStore<Booking> bStore, ObjectStore<Activity> aStore) {
		super(bStore);
		activityStore = aStore;
	}
}
