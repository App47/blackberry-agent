package com.app47.embeddedagent;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ObjectAgentLog extends JSONObject {

	// tags - vector of strings
	public ObjectAgentLog(JSONObject configuration, String sessionID, String level,
			String message, Vector tags, Throwable e) throws JSONException {
		super();
		this.put(ObjectAgentSession.UUID, HelperSessionAgent.generateUUID());
		this.put("session_id", sessionID);
		this.put("level", level);
		this.put("message", message);
		if (!message.equals("Uncaught Exception!")) {
			// TODO
			// StackTraceElement[] stackTrace =
//			 Thread.currentThread().getStackTrace();
			// this.put("filename", stackTrace[5].getFileName());
			// this.put("line_number",
			// String.valueOf(stackTrace[5].getLineNumber()));
		}
		long currentTime = System.currentTimeMillis();
		this.put("start_time", HelperEnvironmentAgent.longToDate(currentTime));
		long offset = Long.parseLong(configuration.get(
				HelperConfigAgent.SERVER_TIME_EPOCH_OFFSET).toString());
		this.put("start_time_epoch", (currentTime / 1000) + offset);

		if (tags != null) {
			this.put("tags", new JSONArray(tags));
		}

		if (e != null) {
			this.put("error_name", e.getClass());
			this.put("error_reason", e.getMessage());
			this.put("error_detail", e.toString());
		}

		double[] loc = HelperEnvironmentAgent.getGPS();
		if (loc != null) {
			this.put("location_lat", loc[0]);
			this.put("location_long", loc[1]);
		}
	}
}