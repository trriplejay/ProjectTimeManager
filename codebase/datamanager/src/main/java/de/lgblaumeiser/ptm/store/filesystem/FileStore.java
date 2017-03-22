/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.store.filesystem;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.Long.compare;
import static java.lang.Long.parseLong;
import static java.lang.Long.valueOf;
import static java.lang.System.getProperty;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.io.FilenameUtils.removeExtension;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * A file base store for random objects
 */
public class FileStore<T> implements ObjectStore<T> {
	private static final String ID = "id";

	private final Gson gsonUtil = new Gson();
	private FileSystemAbstraction filesystemAccess;

	@SuppressWarnings("serial")
	public final Type type = new TypeToken<T>(getClass()) {
	}.getType();

	@Override
	public T store(final T object) {
		checkNotNull(object);
		Object index = getIndexObject(object);
		File targetFile = getFileInformation(index);
		String content = createFileContent(object);

		try {
			filesystemAccess.storeToFile(targetFile, content);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return object;
	}

	@Override
	public Collection<T> retrieveAll() {
		return getAllFiles().stream().map(f -> {
			try {
				return extractFileContent(filesystemAccess.retrieveFromFile(f));
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}).collect(toList());
	}

	private T extractFileContent(final String content) {
		return gsonUtil.fromJson(content, this.type);
	}

	private String createFileContent(final T object) {
		return gsonUtil.toJson(object);
	}

	private File getFileInformation(final Object index) {
		File store = getStore();
		return new File(store, index.toString() + getExtension());
	}

	private Collection<File> getAllFiles() {
		File store = getStore();
		return filesystemAccess.getAllFiles(store, getExtension());
	}

	private String getExtension() {
		return type.getTypeName().substring(type.getTypeName().lastIndexOf('.')).toLowerCase();
	}

	private File getStore() {
		File homepath = new File(getProperty("user.home"));
		checkState(homepath.isDirectory() && homepath.exists());
		File applicationPath = new File(homepath, "." + getProperty("filestore.folder", "file_store"));
		if (!applicationPath.exists()) {
			checkState(applicationPath.mkdir());
		}
		return applicationPath;
	}

	public FileStore<T> setFilesystemAccess(final FileSystemAbstraction filesystemAccess) {
		this.filesystemAccess = filesystemAccess;
		return this;
	}

	private Long getIndexObject(final T object) {
		checkNotNull(object);
		try {
			Field f = object.getClass().getDeclaredField(ID);
			f.setAccessible(true);
			Long returnVal = (Long) f.get(object);
			if (returnVal == -1) {
				f.set(object, getNextId());
			}
			returnVal = (Long) f.get(object);
			f.setAccessible(false);
			return returnVal;
		} catch (IllegalAccessException | IllegalArgumentException | ClassCastException | NoSuchFieldException
				| SecurityException e) {
			throw new IllegalStateException(e);
		}

	}

	private Long getNextId() {
		Optional<String> lastId = getAllFiles().stream().map(f -> removeExtension(f.getName()))
				.max((n1, n2) -> compare(valueOf(n1), valueOf(n2)));
		if (lastId.isPresent()) {
			return parseLong(lastId.get()) + 1;
		}
		return 1L;
	}
}
