/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.io.Files.createTempDir;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import de.lgblaumeiser.ptm.store.filesystem.FileSystemAbstraction;
import de.lgblaumeiser.ptm.store.filesystem.FileSystemAbstractionImpl;

public class FileSystemAbstractionTest {
	private final static String FILENAME = "dummy.test";
	private final static String FOLDERNAME = "dummy.folder";
	private final static String FILECONTENT = "Test Content";

	private final FileSystemAbstraction testee = new FileSystemAbstractionImpl();

	@Test
	public void testStoreAndRetrieve() throws IOException {
		File tempFolder = createTempDir();
		File targetFile = new File(tempFolder, FILENAME);
		try {
			assertTrue(testee.folderAvailable(tempFolder, false));
			testee.storeToFile(targetFile, FILECONTENT);
			assertFalse(testee.folderAvailable(targetFile, false));
			assertFalse(testee.folderAvailable(new File(tempFolder, FOLDERNAME), false));
			assertTrue(testee.folderAvailable(new File(tempFolder, FOLDERNAME), true));
			assertTrue(testee.dataAvailable(targetFile));
			Collection<File> files = testee.getAllFiles(tempFolder, "test");
			assertEquals(1, files.size());
			assertEquals(targetFile, getOnlyElement(files));
			String content = testee.retrieveFromFile(targetFile);
			assertEquals(FILECONTENT, content);
			testee.deleteFile(targetFile);
			assertFalse(targetFile.exists());
		} finally {
			forceDelete(tempFolder);
		}
		assertFalse(tempFolder.exists());
	}
}
