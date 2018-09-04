/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static de.lgblaumeiser.ptm.cli.Utils.assertState;

import java.io.File;

import com.beust.jcommander.IStringConverter;

/**
 * A parameter converter to convert a string to a file object
 */
public class FileConverter implements IStringConverter<File> {
	@Override
	public File convert(final String pathAsString) {
		File file = new File(pathAsString);
		File parent = file.getParentFile();
		if (parent != null) {
			assertState(parent.exists());
		}
		return file;
	}
}
