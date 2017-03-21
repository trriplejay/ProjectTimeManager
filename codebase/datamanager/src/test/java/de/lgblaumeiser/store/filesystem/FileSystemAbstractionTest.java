/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.common.collect.Iterables;

public class FileSystemAbstractionTest {
	private final static String FILENAME = "dummy.test";
	private final static String FILECONTENT = "Test Content";

	private final FileSystemAbstraction testee = new FileSystemAbstractionImpl();

	@Test
	public void testStoreAndRetrieve() throws IOException {
		File tempFolder = FileUtils.getTempDirectory();
		File targetFile = new File(tempFolder, FILENAME);
		try {
			testee.storeToFile(targetFile, FILECONTENT);
			assertTrue(testee.dataAvailable(targetFile));
			Collection<File> files = testee.getAllFiles(tempFolder, "test");
			assertEquals(1, files.size());
			assertEquals(targetFile, Iterables.getOnlyElement(files));
			String content = testee.retrieveFromFile(targetFile);
			assertEquals(FILECONTENT, content);
		} finally {
			FileUtils.forceDelete(targetFile);
		}
		assertFalse(targetFile.exists());
	}
}
