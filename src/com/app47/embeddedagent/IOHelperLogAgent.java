package com.app47.embeddedagent;

import org.json.JSONArray;
import org.json.JSONException;

class IOHelperLogAgent extends IOHelperAgent {

	public static void writeLogs(JSONArray logs) {
		writeFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR
				+ "EmbeddedAgentLogs", logs.toString());
		// send out broadcast action, used for the test app

		//Logging.instance.setupView();
	}

	public static JSONArray loadLogs() {
		String str = (String) readFile(IOHelperAgent.FILE_PATH
				+ IOHelperAgent.FILE_SEPARATOR + "EmbeddedAgentLogs");
		try {
			if (str != null) {
				return new JSONArray(str);
			} else {
				throw new JSONException("");
			}
		} catch (JSONException e) {
			return new JSONArray();
		}

	}

	public static boolean hasCaughtUnhandled() {
		String str = (String) readFile(IOHelperAgent.FILE_PATH
				+ IOHelperAgent.FILE_SEPARATOR
				+ "EmbeddedAgentHasCaughtUnhandled");
		if (str == null) {
			return false;
		} else
			return true;
	}

	public static void setHasCaughtUnhandled(boolean hasCaught) {
		if (hasCaught) {
			writeFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR
					+ "EmbeddedAgentHasCaughtUnhandled", "yes");
		} else {
			delete(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR
					+ "EmbeddedAgentHasCaughtUnhandled");
		}
	}

}
