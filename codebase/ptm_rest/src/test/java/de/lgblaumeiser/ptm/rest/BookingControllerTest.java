/*
 * Copyright 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.rest;

import static com.google.common.io.Files.createTempDir;
import static java.lang.System.setProperty;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test the booking rest controller
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private File tempFolder;

	@Before
	public void before() {
		tempFolder = createTempDir();
		String tempStorage = new File(tempFolder, ".ptm").getAbsolutePath();
		setProperty("filestore.folder", tempStorage);
	}

	@After
	public void after() throws IOException {
		forceDelete(tempFolder);
	}

	@Test
	public void testWithInitialSetupNoActivities() throws Exception {
		mockMvc.perform(get("/bookings")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("[]")));
	}

	@Test
	public void testRoundtripCreateAndRetrieveBooking() throws Exception {
		ActivityRestController.ActivityBody data = new ActivityRestController.ActivityBody();
		data.name = "MyTestActivity";
		data.id = "0815";
		mockMvc.perform(
				post("/activities").contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(data)))
				.andDo(print()).andExpect(status().isCreated());

		LocalDate date = LocalDate.now();
		String dateString = date.format(ISO_LOCAL_DATE);
		BookingRestController.BookingBody booking = new BookingRestController.BookingBody();
		booking.activityId = "1";
		booking.starttime = LocalTime.of(8, 15).format(ISO_LOCAL_TIME);
		mockMvc.perform(post("/bookings/" + dateString).contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(booking))).andDo(print()).andExpect(status().isCreated());

		mockMvc.perform(get("/bookings")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(dateString)));

		mockMvc.perform(get("/bookings/" + dateString)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("MyTestActivity")))
				.andExpect(content().string(containsString("0815")))
				.andExpect(content().string(containsString("starttime")));

		mockMvc.perform(get("/bookings/" + dateString + "/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("MyTestActivity")))
				.andExpect(content().string(containsString("0815")))
				.andExpect(content().string(containsString("starttime")));

		booking.endtime = LocalTime.of(16, 30).format(ISO_LOCAL_TIME);
		mockMvc.perform(post("/bookings/" + dateString + "/1").contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(booking))).andDo(print()).andExpect(status().isOk());

		mockMvc.perform(get("/bookings/" + dateString + "/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("MyTestActivity")))
				.andExpect(content().string(containsString("0815")))
				.andExpect(content().string(containsString("starttime")))
				.andExpect(content().string(containsString("endtime")));

		LocalDate date2 = date.minusDays(1);
		String dateString2 = date2.format(ISO_LOCAL_DATE);

		booking = new BookingRestController.BookingBody();
		booking.activityId = "1";
		booking.starttime = LocalTime.of(8, 15).format(ISO_LOCAL_TIME);
		booking.endtime = LocalTime.of(16, 30).format(ISO_LOCAL_TIME);
		mockMvc.perform(post("/bookings/" + dateString2).contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(booking))).andDo(print()).andExpect(status().isCreated());

		mockMvc.perform(get("/bookings/" + dateString2 + "/2")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("MyTestActivity")))
				.andExpect(content().string(containsString("0815")))
				.andExpect(content().string(containsString("starttime")))
				.andExpect(content().string(containsString("endtime")));

		mockMvc.perform(get("/bookings")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(dateString)))
				.andExpect(content().string(containsString(dateString2)));

		mockMvc.perform(delete("/bookings/" + dateString + "/1")).andDo(print()).andExpect(status().isOk());
		mockMvc.perform(delete("/bookings/" + dateString2 + "/2")).andDo(print()).andExpect(status().isOk());

		mockMvc.perform(get("/bookings")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("[]")));

	}
}
