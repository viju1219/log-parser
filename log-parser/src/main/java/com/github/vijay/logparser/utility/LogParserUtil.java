package com.github.vijay.logparser.utility;

import com.github.vijay.logparser.modal.Event;

public class LogParserUtil {
	
	public static long getEventExecutionTime(Event event1, Event event2) {

		long startTime = 0;
		long endTime = 0;
		if (event1.getState().equalsIgnoreCase("STARTED")) {
			startTime = event1.getTimestamp();
			endTime = event2.getTimestamp();
		} else if (event2.getState().equalsIgnoreCase("STARTED")) {
			startTime = event2.getTimestamp();
			endTime = event1.getTimestamp();
		}

		return endTime - startTime;
	}

}
