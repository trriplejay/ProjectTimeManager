/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import java.time.YearMonth;

import com.beust.jcommander.IStringConverter;

class YearMonthConverter implements IStringConverter<YearMonth> {
	@Override
	public YearMonth convert(final String s) {
		return YearMonth.parse(s);
	}
}
