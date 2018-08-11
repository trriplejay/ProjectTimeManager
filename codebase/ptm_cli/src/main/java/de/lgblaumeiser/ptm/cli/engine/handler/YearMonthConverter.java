/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.IStringConverter;

import java.time.YearMonth;

class YearMonthConverter implements IStringConverter<YearMonth> {
    @Override
    public YearMonth convert(String s) {
        return YearMonth.parse(s);
    }
}
