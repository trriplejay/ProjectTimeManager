/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import static com.google.common.collect.Iterables.getOnlyElement;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;

public class FileStoreTest {
	private static final String TESTINDEX = "TestIndex";
	private static final String TESTCONTENT = "TestContent";

	private FileStore<TestStoreObject> testee;

	private final FileSystemAbstraction stubAccess = new FileSystemAbstraction() {
		@Override
		public void storeToFile(final File target, final String content) {
			storageFile = target;
			storageContent = content;
		}

		@Override
		public String retrieveFromFile(final File source) throws IOException {
			if (!source.equals(storageFile)) {
				throw new IOException();
			}
			if (storageContent == null) {
				throw new IOException();
			}
			return storageContent;
		}

		@Override
		public boolean dataAvailable(final File source) {
			return true;
		}

		@Override
		public Collection<File> getAllFiles(File folder, String extension) {
			return storageFile != null ? asList(storageFile) : emptyList();
		}
	};
	private File storageFile;
	private String storageContent;

	@Before
	public void setUp() {
		testee = new FileStore<TestStoreObject>() {
		};
		testee.setFilesystemAccess(stubAccess);
	}

	private static final TestStoreObject testData = new TestStoreObject(TESTINDEX, TESTCONTENT);

	@Test
	public void testStore() {
		testee.store(testData);
		assertEquals("teststoreobject", FilenameUtils.getExtension(storageFile.getName()));
		assertTrue(storageContent.contains("index"));
		assertTrue(storageContent.contains(TESTINDEX));
		assertTrue(storageContent.contains("data"));
		assertTrue(storageContent.contains(TESTCONTENT));
		assertTrue(storageContent.contains("id"));
	}

	@Test
	public void testRetrieveAll() {
		testee.store(testData);
		Collection<TestStoreObject> foundObjs = testee.retrieveAll();
		assertEquals(1, foundObjs.size());
		TestStoreObject foundObj = getOnlyElement(foundObjs);
		assertEquals(TESTINDEX, foundObj.index);
		assertEquals(TESTCONTENT, foundObj.data);
	}
}

class TestStoreObject {
	final String index;
	final String data;
	final Long id = Long.valueOf(-1);

	TestStoreObject(final String index, final String data) {
		this.index = index;
		this.data = data;
	}
}