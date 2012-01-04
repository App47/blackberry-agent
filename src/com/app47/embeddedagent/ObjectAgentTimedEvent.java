package com.app47.embeddedagent;

import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;


public class ObjectAgentTimedEvent extends ObjectAgentEvent {
	public ObjectAgentTimedEvent(JSONObject event) {
		super(event);
	}

	public ObjectAgentTimedEvent(JSONObject config, String name, Vector tags,
			double[] loc) throws JSONException {
		super(config, name, tags, loc);
		long time = System.currentTimeMillis();
		this.put("start_time_raw", time);
		this.put("duration", -1);
	}

	public long getDuration() {
		try {
			return this.getLong("duration" + "");
		} catch (JSONException e) {
			return -1;
		}
	}

	public void finish() {
		try {
			double duration = System.currentTimeMillis() - getStartTime();
			duration = duration / 1000;
			this.put("duration", duration);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public boolean isFinished() {
		long duration = getDuration();
		if (duration > 0)
			return true;
		else
			return false;
	}
}
