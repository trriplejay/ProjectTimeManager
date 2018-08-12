/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkState;

/**
 * Utils to do rest calls on the rest api
 */
public class RestUtils {
	private static final int TIMEOUT = 5 * 1000; // 5 times 1000 msec

	private CloseableHttpClient clientConnector;
	private String baseUrl;

	private ObjectMapper jsonMapper;

	private Properties applicationProps;

	/**
	 * Post a call to the rest api. Expects that a numerical id is returned as
	 * part of the creation call.
	 * 
	 * @param apiName
	 *            Name of the api
	 * @param bodyData
	 *            Body of the post data, this is a flat map that is converted
	 *            into a flat json
	 * @return The Id of the created or manipulated object
	 */
	public Long post(String apiName, Map<String, String> bodyData) {
		try {
			final HttpPost request = new HttpPost(baseUrl + apiName);
			StringEntity bodyJson = new StringEntity(jsonMapper.writeValueAsString(bodyData), "UTF-8");
			bodyJson.setContentType("application/json");
			bodyJson.setContentEncoding("UTF-8");
			request.setEntity(bodyJson);
			request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 201 || response.getStatusLine().getStatusCode() == 200, response);
			String uri = apiName;
			if (response.getStatusLine().getStatusCode() == 201) {
				uri = response.getHeaders("Location")[0].getValue();
			}
			return Long.parseLong(uri.substring(uri.lastIndexOf("/") + 1));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

	/**
	 * Returns an element or an array of elements depending on the returnClass
	 * 
	 * @param apiName
	 *            The api name of the get call
	 * @param returnClass
	 *            The class object of a result type
	 * @return The found element or array of elements
	 */
	public <T> T get(String apiName, Class<T> returnClass) {
		try {
			final HttpGet request = new HttpGet(baseUrl + apiName);
			request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 200, response.getStatusLine());
			return jsonMapper.readValue(new InputStreamReader(response.getEntity().getContent()), returnClass);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

	/**
	 * Delete an entity via a rest call
	 * 
	 * @param apiName
	 *            The api name for the deletion
	 */
	public void delete(String apiName) {
		try {
			final String requestString = baseUrl + apiName;
			final HttpDelete request = new HttpDelete(requestString);
			request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 200, response);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Constructor, creates the HTTP Client object to execute http rest requests
	 */
	public RestUtils configure() {
		final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT)
				.setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
		clientConnector = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		applicationProps = loadAppProps();
		String host = getProperty("ptm.host");
		String port = getProperty("ptm.port");
		baseUrl = "http://" + host + ":" + port;
		jsonMapper = new ObjectMapper();
		jsonMapper.registerModule(new JavaTimeModule());
		return this;
	}

	private Properties loadAppProps() {
		Properties applicationProps = new Properties();
		try (InputStream in = getClass().getResourceAsStream("rest.properties")) {
			applicationProps.load(in);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return applicationProps;
	}

	private String getProperty(String key) {
		String prop = System.getenv(key);
		prop = (prop == null) ? System.getProperty(key) : prop;
		prop = (prop == null) ? applicationProps.getProperty(key) : prop;
		checkState(prop != null);
		return prop;
	}
}
