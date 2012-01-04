package com.app47.embeddedagent;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceConfigAgent {
	public final static int REQ_UPDATE = 101;
	public final static int REQ_APP_ID = 102;
	public final static int REQ_RES = 103;

	public static void handleRequest(int request, String appID) {
		JSONObject config = IOHelperConfigAgent.loadConfig();
		switch (request) {
		case REQ_UPDATE:
			IOHelperConfigAgent.registerAgent();

			IOHelperConfigAgent.updateGroups();

			break;
		case REQ_APP_ID:
			setupConfigWithDefaults(appID);
			break;
		case REQ_RES:
			try {
				if (config != null) {
					String agentId = (String) config
							.get(JSONKeys.REG_RESPONSE_AGENT_ID);
				} else {
					throw new JSONException("");
				}
			} catch (JSONException e) {
				setupConfigFromResources();
			}
			handleRequest(REQ_UPDATE, null);
			break;
		default:
			break;
		}
	}

	public static void setupConfigWithDefaults(String appID) {
		JSONObject defaultConfig = null;
		try {
			String str = IOHelperConfigAgent.readFile(IOHelperAgent.FILE_PATH
					+ IOHelperAgent.FILE_SEPARATOR
					+ IOHelperAgent.CONFIG_FILE_NAME);

			if (str != null) {
				defaultConfig = new JSONObject(str);
			} else {
				return;
			}
			// ds config, updates and writes defaults in case of appID change
			defaultConfig.put(JSONKeys.APP_ID, appID);
			defaultConfig.put(JSONKeys.UPDATE_FRQ,
					UtilAgentConstants.DEFAULT_UPDATE_FRQ);
			defaultConfig.put(JSONKeys.UPLOAD_DELAY,
					UtilAgentConstants.DEFAULT_UPLOAD_DELAY);
			defaultConfig.put(JSONKeys.SHOW_IDENTIFIER,
					UtilAgentConstants.DEFAILT_SEND_IDENTIFIER);
			IOHelperConfigAgent.writeConfigDefault(defaultConfig);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void setupConfigFromResources() {

		String defaultConfig = IOHelperConfigAgent
				.readFile(IOHelperAgent.FILE_PATH
						+ IOHelperAgent.FILE_SEPARATOR
						+ IOHelperAgent.CONFIG_FILE_NAME);
		if (defaultConfig != null) {
			JSONObject jsn;
			try {
				jsn = new JSONObject(defaultConfig);
				if (jsn.length() < 4) {
					IOHelperConfigAgent.buildWriteDefaultConfig();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			IOHelperConfigAgent.buildWriteDefaultConfig();
		}
	}
}
