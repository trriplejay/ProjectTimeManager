/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beust.jcommander.ParameterException;

public class BackupRestoreTest extends AbstractHandlerTest {
	private static final String BACKUP_COMMAND = "backup";
	private static final String RESTORE_COMMAND = "restore";

	private static final String TESTDATACONTENT = "This is some crazy test data that should be returned properly";

	private File tempfolder;

	@Before
	public void before() throws IOException {
		super.before();
		tempfolder = Files.createTempDirectory("ptm").toFile();
	}

	@After
	public void after() throws IOException {
		FileUtils.forceDelete(tempfolder);
	}

	@Test
	public void testBackupRestore() throws IOException {
		File tempfile = createDataFile();
		File targetfile = new File(tempfolder, "ptm_target.zip");
		commandline.runCommand(RESTORE_COMMAND, "-z", tempfile.getAbsolutePath());
		commandline.runCommand(BACKUP_COMMAND, "-z", targetfile.getAbsolutePath());
		String result = FileUtils.readFileToString(targetfile, "UTF-8");
		assertEquals(TESTDATACONTENT, result);
	}

	private File createDataFile() throws IOException {
		File datafile = new File(tempfolder, "ptm_backup.zip");
		FileUtils.write(datafile, TESTDATACONTENT, "UTF-8");
		return datafile;
	}

	@Test(expected = ParameterException.class)
	public void testBackupWithoutZipfile() {
		commandline.runCommand(BACKUP_COMMAND);
	}

	@Test(expected = ParameterException.class)
	public void testRestoreWithoutZipfile() {
		commandline.runCommand(RESTORE_COMMAND);
	}
}