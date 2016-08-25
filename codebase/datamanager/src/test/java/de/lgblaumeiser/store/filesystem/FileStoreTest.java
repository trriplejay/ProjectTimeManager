package de.lgblaumeiser.store.filesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;

public class FileStoreTest {
    private static final String TESTINDEX = "TestIndex";
    private static final String TESTCONTENT = "TestContent";
    private static final File STORAGEPLACE = FileUtils.getTempDirectory();

    private FileStore<TestStoreObject> testee;
    private final Properties testProps = new Properties();

    private final FileSystemAbstraction stubAccess = new FileSystemAbstraction() {
	@Override
	public void storeToFile(@NonNull final File target, @NonNull final String content) {
	    storageFile = target;
	    storageContent = content;
	}
    };
    private File storageFile;
    private String storageContent;

    @SuppressWarnings("null")
    @Before
    public void setUp() {
	testee = new FileStore<>();
	testProps.setProperty(FileStore.INDEX_KEY, "index");
	testProps.setProperty(FileStore.CLASS_KEY, TestStoreObject.class.getName());
	testProps.setProperty(FileStore.STORAGE_PATH_KEY, STORAGEPLACE.getAbsolutePath());
	testee.configure(testProps);
	testee.setFilesystemAccess(stubAccess);
    }

    @NonNull
    private static final TestStoreObject testData = new TestStoreObject(TESTINDEX, TESTCONTENT);

    @Test
    public void testStore() {
	testee.store(testData);
	assertTrue(storageFile.getName().startsWith(TESTINDEX));
	assertEquals(STORAGEPLACE, storageFile.getParentFile());
	assertTrue(storageContent.contains("index"));
	assertTrue(storageContent.contains(TESTINDEX));
	assertTrue(storageContent.contains("data"));
	assertTrue(storageContent.contains(TESTCONTENT));
    }

    @Test
    public void testRetrieveByIndexKey() {
	fail("Not yet implemented");
    }

}

class TestStoreObject {
    private final String index;
    private final String data;

    TestStoreObject(final String index, final String data) {
	this.index = index;
	this.data = data;
    }

    public String getIndex() {
	return index;
    }

    public String getData() {
	return data;
    }
}