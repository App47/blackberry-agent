package com.app47.embeddedagent;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class IOHelperSessionAgent extends IOHelperAgent {

	public static void writeSessionEnd(long time) {
		String t = String.valueOf(time);
		JSONObject json = new JSONObject();
		try {
			json.put(JSONKeys.SESSION_END_TIME, t);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		writeFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR
				+ "EmbeddedAgentsession_end", json.toString());
	}

	public static long loadSessionEnd() {
		try {
			String str = readFile(IOHelperAgent.FILE_PATH
					+ IOHelperAgent.FILE_SEPARATOR + "EmbeddedAgentsession_end");

			if (str != null) {
				JSONObject json = new JSONObject(str);

				return Long
						.parseLong(json.getString(JSONKeys.SESSION_END_TIME));
			} else {
				return -1;
			}
		} catch (NullPointerException e) {
			return -1;
		} catch (JSONException e) {
			return -1;
		}
	}

	public static void writeSessionStart(long time) {
		String timeS = String.valueOf(time);
		JSONObject json = new JSONObject();
		try {
			json.put(JSONKeys.SESSION_START_TIME, timeS);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		writeFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR
				+ "EmbeddedAgentsession_start", json.toString());
	}

	public static long loadSessionStart() {
		try {
			String str = readFile(IOHelperAgent.FILE_PATH
					+ IOHelperAgent.FILE_SEPARATOR
					+ "EmbeddedAgentsession_start");

			if (str != null) {
				return Long.parseLong(new JSONObject(str).get(
						JSONKeys.SESSION_START_TIME).toString());
			} else {
				return -1;
			}
		} catch (JSONException e) {
			return -1;
		}
	}

	public static void writeCurrentSessionID(String id) {
		writeFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR
				+ "EmbeddedAgentcurrent_session_id", id);
	}

	public static String loadCurrentSessionStartID() {
		try {
			return (String) readFile(IOHelperAgent.FILE_PATH
					+ IOHelperAgent.FILE_SEPARATOR
					+ "EmbeddedAgentcurrent_session_id");
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static void writeSessions(Vector sessions) throws JSONException {
		// session is a Vector of Agent Session
		JSONArray jArray = new JSONArray();

		int size = sessions.size();

		for (int i = size - 1; i >= 0; i--) {
			jArray.put(((ObjectAgentSession)sessions.elementAt(i)).toSimple());
		}

		writeFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR
				+ "EmbeddedAgentsessions", jArray.toString());
		// send out broadcast action, used for the test app
	}

	public static Vector loadSessions() {
		try {
			String read = IOHelperAgent.readFile(IOHelperAgent.FILE_PATH
					+ IOHelperAgent.FILE_SEPARATOR + "EmbeddedAgentsessions");

			if (read != null) {
				JSONArray jArray = null;
				int size = -1;
				try {
					jArray = new JSONArray(read);
				} catch (JSONException e1) {
					size = 0;
				}
				size = jArray.length();

				Vector sessions = new Vector();

				for (int i = size - 1; i >= 0; i--) {
					ObjectAgentSession as = new ObjectAgentSession();
					try {
						JSONObject jSon = new JSONObject((String) jArray.get(i));
						// set attributes

						as.setUuid(jSon.get(ObjectAgentSession.UUID).toString());

						// long startTime =
						// HttpDateParser.parse(jSon.get(AgentSession.START_TIME).toString());
						long startTime = Long.parseLong(jSon.get(
								ObjectAgentSession.START_TIME).toString());

						long duration = Long.parseLong(jSon.get(
								ObjectAgentSession.DURATION).toString());
						as.setStartTime(startTime);
						as.setDuration(duration);
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (NumberFormatException e) {

					}

					sessions.addElement(as);
				}
				return sessions;
			}

			return null;

		} catch (NullPointerException e) {
			return null;
		}
	}

}