/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */

package de.lgblaumeiser.ptm.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Class with some static utils methods to get rid of apache and guava
 * dependencies
 * 
 * Not really well designed a bunch of unrelated one liners
 */
public class Utils {
	public static boolean stringHasContent(String toBeChecked) {
		return (toBeChecked != null) && !toBeChecked.isEmpty();
	}

	public static String emptyString() {
		return "";
	}

	public static void assertState(boolean condition) {
		if (!condition) {
			throw new IllegalStateException();
		}
	}

	public static void assertState(boolean condition, Object message) {
		if (!condition) {
			throw new IllegalStateException(message.toString());
		}
	}

	public static <T> T getIndexFromCollection(Collection<T> col, int index) {
		Iterator<T> iter = col.iterator();
		int cur = 0;
		while (iter.hasNext()) {
			T item = iter.next();
			if (cur == index) {
				return item;
			}
			cur++;
		}
		throw new IllegalStateException();
	}

	public static <T> T getFirstFromCollection(Collection<T> col) {
		return getIndexFromCollection(col, 0);
	}

	public static <T> T getLastFromCollection(Collection<T> col) {
		return getIndexFromCollection(col, col.size() - 1);
	}

	public static <T> T getOnlyFromCollection(Collection<T> col) {
		assertState(col.size() == 1);
		return getFirstFromCollection(col);
	}
}
