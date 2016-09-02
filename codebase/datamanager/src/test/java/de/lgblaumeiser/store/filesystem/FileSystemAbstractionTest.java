/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

public class FileSystemAbstractionTest {
    private final static String FILENAME = "dummy.test";
    @NonNull
    private final static String FILECONTENT = "Test Content";

    private final FileSystemAbstraction testee = new FileSystemAbstractionImpl();

    @Test
    public void testStoreAndRetrieve() throws IOException {
	File tempFolder = FileUtils.getTempDirectory();
	File targetFile = new File(tempFolder, FILENAME);
	try {
	    testee.storeToFile(targetFile, FILECONTENT);
	    assertTrue(testee.dataAvailable(targetFile));
	    String content = testee.retrieveFromFile(targetFile);
	    assertEquals(FILECONTENT, content);
	} finally {
	    FileUtils.forceDelete(targetFile);
	}
	assertFalse(targetFile.exists());
    }
}
