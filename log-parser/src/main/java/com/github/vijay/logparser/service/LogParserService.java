package com.github.vijay.logparser.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vijay.logparser.modal.Alert;
import com.github.vijay.logparser.modal.Event;
import com.github.vijay.logparser.repository.LogParserRepository;
import com.github.vijay.logparser.utility.LogParserUtil;

@Service
public class LogParserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogParserService.class);

	@Autowired
	LogParserRepository repo;

	@Value("${log-parser.threshold-ms}")
	public int threshold;

	public void process(String... args) {
		validateFilePath(args[0]);
		processEvents(args[0]);
	}

	private void validateFilePath(String logFilePath) {

		try {
			File file = new File(logFilePath);
			if (file.exists())
				LOGGER.info("File is Available");
			else
				throw new FileNotFoundException("Unable to Locate the file " + logFilePath);
		} catch (IOException e) {
			LOGGER.error("IOException occured", e);
		}
	}

	public void processEvents(String logFilePath) {

		Map<String, Event> eventMap = new HashMap<>();
		Map<String, Alert> alertMap = new HashMap<>();

		try {
			LineIterator li = FileUtils.lineIterator(new File(logFilePath));
			while (li.hasNext()) {
				Event event = new ObjectMapper().readValue(li.nextLine(), Event.class);// Parse Json to Java Object

				if (eventMap.containsKey(event.getId())) {
					Event e = eventMap.get(event.getId());
					long executionTime = LogParserUtil.getEventExecutionTime(event, e);
					LOGGER.debug("Execution time - {}", executionTime);
					Alert alert = new Alert(event, executionTime);

					if (executionTime > threshold) { // Threshold limit in millisecond
						alert.setAlert(true);
					}

					alertMap.put(event.getId(), alert);

					eventMap.remove(event.getId()); // Remove the event from Event Map after adding it to Alert Map
				} else {
					eventMap.put(event.getId(), event);
				}
			}
			if (!alertMap.isEmpty()) {
				insertEvents(alertMap);
			}else {
				LOGGER.info("No data to be inserted");
			}
		} catch (JsonProcessingException ex) {
			LOGGER.error("JsonProcessingException Occured", ex);
		} catch (IOException ex) {
			LOGGER.error("IOException Occured", ex);
		}
	}

	public void insertEvents(Map<String, Alert> alertMap) {
		repo.saveAll(alertMap.values());
		LOGGER.info("{} Data Inserted Successfully",alertMap.size());
	}

}
