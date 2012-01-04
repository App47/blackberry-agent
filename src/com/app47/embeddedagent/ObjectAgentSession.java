package com.app47.embeddedagent;

import org.json.JSONException;
import org.json.JSONObject;

class ObjectAgentSession {
	public final static String UUID = "uuid";
	public final static String START_TIME = "start_time";
	public final static String END_TIME = "end_time";
	public final static String DURATION = "duration";
	public final static String LAT = "latitude";
	public final static String LONG = "longitude";

	private long startTime;
	private long duration;
	private String uuid;
	private double latitude;
	private double longitude;
	private boolean hasLocation = false;
	private long startTimeEpochSeconds;

	public ObjectAgentSession() {
	}

	public long getStartTimeEpochSeconds() {
		return startTimeEpochSeconds;
	}

	public void setStartTimeEpochSeconds(long sessionEpochTimeSeconds) {
		this.startTimeEpochSeconds = sessionEpochTimeSeconds;
	}

	public double[] getLocation() {
		double[] loc = null;
		if (hasLocation) {
			loc = new double[2];
			loc[0] = this.latitude;
			loc[1] = this.longitude;
		}
		return loc;
	}

	public void setLocation(double[] loc) {
		this.latitude = loc[0];
		this.longitude = loc[1];
		hasLocation = true;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String toStringPost(String agentId) {
		StringBuffer buf = new StringBuffer();
		
		buf.append("agent_id=");
		buf.append(agentId);
		buf.append("&");
		buf.append(DURATION);
		buf.append("=");
		buf.append(duration);
		buf.append("&");
		buf.append(uuid);
		buf.append("=");
		buf.append(uuid);
		buf.append("&");
		buf.append(START_TIME);
		buf.append("=");
		buf.append(startTime);
		
		return buf.toString();
	}
	
	/**
	 * returns Json without Timezone
	 * @return
	 */
	public String toSimple(){
		JSONObject obj = new JSONObject();

		try {
			obj.accumulate(UUID, uuid);

			obj.accumulate(START_TIME, String.valueOf(startTime));
			obj.accumulate(END_TIME, String.valueOf(startTime + duration));
			obj.accumulate(DURATION, String.valueOf(duration));// + "s");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj.toString();
		
	}
	
	public String toString() {
		JSONObject obj = new JSONObject();

		try {
			obj.accumulate(UUID, uuid);

			obj.accumulate(START_TIME, //String.valueOf(startTime));
					HelperEnvironmentAgent.longToDate(startTime));
			obj.accumulate(END_TIME, //String.valueOf(startTime + duration));
					HelperEnvironmentAgent.longToDate(startTime + duration * 1000));
			obj.accumulate(DURATION, String.valueOf(duration));// + "s");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj.toString();
	}

	public boolean equals(Object aThat) {
		if (this == aThat)
			return true;

		if (!(aThat instanceof ObjectAgentSession))
			return false;

		ObjectAgentSession that = (ObjectAgentSession) aThat;
		return (this.getDuration() == that.getDuration())
				&& (this.getStartTime() == that.getStartTime())
				&& (this.getUuid().equals(that.getUuid()));
	}
}