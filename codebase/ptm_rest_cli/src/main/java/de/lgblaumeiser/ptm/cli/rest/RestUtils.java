/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.rest;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * Utils to do rest calls on the rest api
 */
public class RestUtils {
	private static final int TIMEOUT = 5 * 1000; // 5 times 1000 msec

	private final CloseableHttpClient clientConnector;
	private final String baseUrl;

	private final ObjectMapper jsonMapper;

	private final Properties applicationProps;

	/**
	 * Send a post request to the configured server to create new activity
	 * 
	 * @param name
	 *            Name of new activity
	 * @param number
	 *            booking number of new activity
	 * @return The id of the new activity
	 */
	public String postActivity(final String name, final String number) {
		try {
			final HttpPost request = new HttpPost(baseUrl + "/activities");
			Map<String, String> bodyData = newHashMap();
			bodyData.put("name", name);
			bodyData.put("id", number);
			StringEntity bodyJson = new StringEntity(jsonMapper.writeValueAsString(bodyData));
			bodyJson.setContentType("application/json");
			bodyJson.setContentEncoding("UTF-8");
			request.setEntity(bodyJson);
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 201,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: /activities");
			String uri = response.getHeaders("Location")[0].getValue();
			return uri.substring(uri.lastIndexOf("/") + 1);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * @return A collection of all activities stored in the service
	 */
	public Collection<Activity> getActivities() {
		try {
			final HttpGet request = new HttpGet(baseUrl + "/activities");
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 200,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: /activities");
			return asList(
					jsonMapper.readValue(new InputStreamReader(response.getEntity().getContent()), Activity[].class));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public String postBooking(String dayString, String activityId, LocalTime starttime, Optional<LocalTime> endtime) {
		try {
			final String requestString = baseUrl + "/bookings/" + dayString;
			final HttpPost request = new HttpPost(requestString);
			Map<String, String> bodyData = newHashMap();
			bodyData.put("activityId", activityId);
			bodyData.put("starttime", starttime.format(ISO_LOCAL_TIME));
			endtime.ifPresent(time -> bodyData.put("endtime", time.format(ISO_LOCAL_TIME)));
			StringEntity bodyJson = new StringEntity(jsonMapper.writeValueAsString(bodyData));
			bodyJson.setContentType("application/json");
			bodyJson.setContentEncoding("UTF-8");
			request.setEntity(bodyJson);
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 201,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: " + requestString);
			String uri = response.getHeaders("Location")[0].getValue();
			return uri.substring(uri.lastIndexOf("/") + 1);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public List<Booking> getBookingsForDay(String dayString) {
		try {
			final String requestString = baseUrl + "/bookings/" + dayString;
			final HttpGet request = new HttpGet(requestString);
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 200,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: " + requestString);
			String jsonData = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			return asList(jsonMapper.readValue(jsonData, Booking[].class));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public void deleteBooking(String dayString, String bookingId) {
		try {
			final String requestString = baseUrl + "/bookings/" + dayString + "/" + bookingId;
			final HttpDelete request = new HttpDelete(requestString);
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 200,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: " + requestString);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public void postBookingEnd(String dayString, String bookingId, LocalTime endtime) {
		try {
			final String requestString = baseUrl + "/bookings/" + dayString + "/" + bookingId;
			final HttpPost request = new HttpPost(requestString);
			Map<String, String> bodyData = newHashMap();
			bodyData.put("endtime", endtime.format(ISO_LOCAL_TIME));
			StringEntity bodyJson = new StringEntity(jsonMapper.writeValueAsString(bodyData));
			bodyJson.setContentType("application/json");
			bodyJson.setContentEncoding("UTF-8");
			request.setEntity(bodyJson);
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 200,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: " + requestString);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public Collection<Collection<Object>> getAnalysisResult(String monthString, String analysisString) {
		try {
			final String requestString = baseUrl + "/analysis/" + analysisString + "/" + monthString;
			final HttpGet request = new HttpGet(requestString);
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 200,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: " + requestString);
			String jsonData = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			Object[][] resultData = jsonMapper.readValue(jsonData, Object[][].class);
			return convertToCollection(resultData);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private Collection<Collection<Object>> convertToCollection(Object[][] resultData) {
		Collection<Collection<Object>> converted = newArrayList();
		for (Object[] currentLine : resultData) {
			converted.add(asList(currentLine));
		}
		return converted;
	}

	/**
	 * Constructor, creates the HTTP Client object to execute http rest requests
	 */
	public RestUtils() {
		final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT)
				.setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
		clientConnector = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		applicationProps = loadAppProps();
		String host = getProperty("ptm.host");
		String port = getProperty("ptm.port");
		baseUrl = "http://" + host + ":" + port;
		jsonMapper = new ObjectMapper();
		jsonMapper.registerModule(new JavaTimeModule());
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
