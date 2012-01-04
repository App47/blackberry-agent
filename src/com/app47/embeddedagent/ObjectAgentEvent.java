package com.app47.embeddedagent;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 */
class ObjectAgentEvent extends JSONObject {
	// JSONObject list = new JSONObject();

	public ObjectAgentEvent(JSONObject event) {
		// TODO load from event jso
		try {
			this.put("start_time", event.get("start_time"));
			this.put("server_time_off", event.get("server_time_off"));
			this.put("start_time_epoch", event.get("start_time_epoch"));
			this.put("name", event.get("name"));
			this.put("uuid", event.get("uuid"));
			if (event.get("location_lat") != null) {
				this.put("location_lat", event.get("location_lat"));
			}
			if (event.get("tags") != null) {
				this.put("tags", event.get("tags"));

			}
			try {
				if (event.get("location_long") != null) {
					this.put("location_long", event.get("location_long"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObjectAgentEvent(JSONObject config, String name, Vector tags, double loc[])
			throws JSONException {
		long currentTime = System.currentTimeMillis();
		this.put("start_time", HelperEnvironmentAgent.longToDate(currentTime));
		Object ss = config.get(HelperConfigAgent.SERVER_TIME_EPOCH_OFFSET);
		this.put("server_time_off",
				config.get(HelperConfigAgent.SERVER_TIME_EPOCH_OFFSET));
		long offset = Long.parseLong((config
				.get(HelperConfigAgent.SERVER_TIME_EPOCH_OFFSET)).toString());
		// /this.put(", value);
		this.put("start_time_epoch", (currentTime / 1000) + offset);

		this.put("name", name);
		this.put("uuid", HelperSessionAgent.generateUUID());
		if (loc != null) {
			this.put("location_lat", loc[0]);
			this.put("location_long", loc[1]);
		}
		if (tags != null) {
			this.put("tags", new JSONArray(tags));

		}

	}

	// public AgentEvent(String name, List<String> tags) throws JSONException {
	// this(name, tags, null);
	// }
	//
	// public AgentEvent(String name, double[] loc) throws JSONException {
	// this(name, null, loc);
	// }
	//
	// public AgentEvent(String name) throws JSONException {
	// this(name, null, null);
	// }

	public String getName() {
		try {
			return this.getString("name");
		} catch (JSONException e) {
			return null;
		}
	}

	public JSONArray getTags() {
		try {
			return this.getJSONArray("tags");
		} catch (JSONException e) {
			return null;
		}
	}

	public void addTags(Vector addTags) {

		JSONArray tags = this.getTags();
		for (int x = 0; x < addTags.size(); x++) {
			tags.put(addTags.elementAt(x));
		}

		try {
			this.put("tags", tags);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public double getLat() {
		try {
			return this.getDouble("location_lat");
		} catch (JSONException e) {
			return Double.NaN;
		}
	}

	public double getLong() {
		try {
			return this.getDouble("location_long");
		} catch (JSONException e) {
			return Double.NaN;
		}
	}

	public String getUuid() {
		try {
			return this.getString("uuid");
		} catch (JSONException e) {
			return null;
		}
	}

	public long getStartTime() {
		try {
			return this.getLong("start_time_raw");
		} catch (JSONException e) {
			return -1;
		}
	}
}
