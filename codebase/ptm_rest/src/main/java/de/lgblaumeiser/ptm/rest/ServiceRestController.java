/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * General rest services of the PTM backend
 */
@RestController
@RequestMapping("/services")
public class ServiceRestController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ServiceMapper services;

	@RequestMapping(method = RequestMethod.GET, value = "/backup")
	public ResponseEntity<?> backup() {
		logger.info("Request: Get backup of data");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/zip"));
		String outputFilename = "ptm_backup.zip";
		headers.setContentDispositionFormData(outputFilename, outputFilename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		services.backupService().backup(byteOutputStream);
		logger.info("Result: Backup Data acquired, return to requester");
		return ResponseEntity.ok().headers(headers).body(byteOutputStream.toByteArray());
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/restore", headers = "content-type=multipart/*", consumes = "application/zip")
	public ResponseEntity<?> restore(InputStream zipdata) {
		logger.info("Request: Put data to restore database");
		services.backupService().emptyDatabase();
		services.backupService().restore(zipdata);
		logger.info("Result: Data restored");
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		logger.error("Exception in Request", e);
		return ResponseEntity.status(BAD_REQUEST).body(e.toString());
	}
}
