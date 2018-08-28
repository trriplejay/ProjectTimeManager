/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.store.filesystem;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

import de.lgblaumeiser.ptm.store.ObjectStore;
import de.lgblaumeiser.ptm.store.StoreBackupRestore;

/**
 * A file base store for random objects
 */
public class FileStore<T> implements ObjectStore<T>, StoreBackupRestore<T> {
	private static final String ID = "id";

	private final ObjectMapper jsonUtil = new ObjectMapper();
	private FilesystemAbstraction filesystemAccess;

	@SuppressWarnings({ "serial", "unchecked" })
	public final Class<T> type = (Class<T>)new TypeToken<T>(getClass()) {
	}.getRawType();

	public FileStore(final FilesystemAbstraction filesystemAccess) {
		jsonUtil.registerModule(new JavaTimeModule());
		this.filesystemAccess = filesystemAccess;
	}

	@Override
	public Collection<T> retrieveAll() {
		return getAllFiles().stream().map(f -> {
			try {
				return extractFileContent(filesystemAccess.retrieveFromFile(f));
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<T> retrieveById(Long id) {
		checkState(id != null);
		File searchedFile = getFileInformation(id);
		if (!filesystemAccess.dataAvailable(searchedFile)) {
			return Optional.empty();
		}
		try {
			return Optional.of(extractFileContent(filesystemAccess.retrieveFromFile(searchedFile)));
		} catch (IOException e) {
			return Optional.empty();
		}
	}

	@Override
	public T store(final T object) {
		checkState(object != null);
		Long index = getIndexObject(object);
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
	public void deleteById(Long id) {
		checkState(id != null);
		File deleteFile = getFileInformation(id);
		checkState(filesystemAccess.dataAvailable(deleteFile));
		try {
			filesystemAccess.deleteFile(deleteFile);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Map<String, String> backup() {
		Map<String, String> backupResult = Maps.newHashMap();
		getAllFiles().stream().forEach(f -> {
			try {
				backupResult.put(f.getName(), filesystemAccess.retrieveFromFile(f));
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		});
		return backupResult;
	}

	@Override
	public void restore(Map<String, String> filenameToContentMap) {
		checkState(getAllFiles().size() == 0);
		filenameToContentMap.keySet().stream().forEach(k -> {
			File targetFile = new File(getStore(), k);
			try {
				filesystemAccess.storeToFile(targetFile, filenameToContentMap.get(k));				
			} catch (IOException e) {
				throw new IllegalStateException();
			}
		});
	}

	@Override
	public void delete() {
		getAllFiles().stream().forEach(f -> {
			try {
				filesystemAccess.deleteFile(f);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		});
	}

	private T extractFileContent(final String content) {
		try {
			return jsonUtil.readValue(content, this.type);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private String createFileContent(final T object) {
		try {
			return jsonUtil.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	private File getFileInformation(final Long index) {
		File store = getStore();
		return new File(store, index.toString() + "." + getExtension());
	}

	private Collection<File> getAllFiles() {
		File store = getStore();
		return filesystemAccess.getAllFiles(store, getExtension());
	}

	private String getExtension() {
		return type.getTypeName().substring(type.getTypeName().lastIndexOf('.') + 1).toLowerCase();
	}

	private File getStore() {
		String storepath = System.getProperty("ptm.filestore");
		File applicationPath = StringUtils.isNotBlank(storepath) ? new File(storepath) : getDefaultStore();
		checkState(filesystemAccess.folderAvailable(applicationPath, true));
		return applicationPath;
	}

	private File getDefaultStore() {
		File homepath = new File(System.getProperty("user.home"));
		checkState(filesystemAccess.folderAvailable(homepath, false));
		return new File(homepath, ".ptm");
	}

	private Long getIndexObject(final T object) {
		checkState(object != null);
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
		Optional<String> lastId = getAllFiles().stream().map(f -> FilenameUtils.removeExtension(f.getName()))
				.max((n1, n2) -> Long.compare(Long.valueOf(n1), Long.valueOf(n2)));
		if (lastId.isPresent()) {
			return Long.parseLong(lastId.get()) + 1;
		}
		return 1L;
	}
}
