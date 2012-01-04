package com.app47.embeddedagent;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


class IOHelperConfigAgent extends IOHelperAgent {
	IOHelperAgent confWriter;

	public IOHelperConfigAgent(String data, boolean update) {
	}

	public static JSONObject createInitialRegisterObject() {
		JSONObject object = new JSONObject();

		try {
			object.accumulate(JSONKeys.REGISTER_DEVICE_MODEL,
					UtilAgentConstants.deviceName);
			object.accumulate(JSONKeys.REGISTER_APP_VERSION,
					UtilAgentConstants.appVersion);
			object.accumulate(JSONKeys.REGISTER_DEVICE_MANUFACTURE,
					UtilAgentConstants.manufacturer);
			object.accumulate(JSONKeys.REGISTER_DEVICE_OS_VERSION,
					UtilAgentConstants.osVersion);
			object.accumulate(JSONKeys.REGISTER_DEVICE_CAPACITY,
					UtilAgentConstants.getDeviceCapacity());
			object.accumulate(JSONKeys.REGISTER_APP_ID, UtilAgentConstants.appID);
			object.accumulate(JSONKeys.REIGSTER_DEVICE_IDENTIFIER,
					UtilAgentConstants.devicePIN);
			object.accumulate(JSONKeys.REGISTER_DEVICE_PLATFORM,
					UtilAgentConstants.os);
			object.accumulate(JSONKeys.REGISTER_AGENT_VERSION,
					UtilAgentConstants.agentVersion);
			object.accumulate(JSONKeys.REIGSTER_DEVICE_UNIQUE_IDENTIFIER,
					UtilAgentConstants.devicePIN);

			return object;
		} catch (JSONException e) {
			return null;
		}
	}

	public static void buildWriteDefaultConfig() {
		JSONObject defaultConfig = new JSONObject();
		try {
			defaultConfig.accumulate(JSONKeys.APP_ID, UtilAgentResourceGetter
					.getString(UtilAgentResourceGetter.application_id));
			defaultConfig.accumulate(JSONKeys.UPDATE_FRQ, UtilAgentResourceGetter
					.getString(UtilAgentResourceGetter.res_agent_update));
			defaultConfig.accumulate(JSONKeys.UPLOAD_DELAY, UtilAgentResourceGetter
					.getString(UtilAgentResourceGetter.upload_delay));
			defaultConfig
					.accumulate(
							JSONKeys.SHOW_IDENTIFIER,
							UtilAgentResourceGetter
									.getString(UtilAgentResourceGetter.send_device_identifier));
			defaultConfig
					.accumulate(JSONKeys.CONFIGURATION_ENDPOINT,
							UtilAgentResourceGetter
									.getString(UtilAgentResourceGetter.endpoint));

			IOHelperAgent.writeFile(IOHelperAgent.FILE_PATH
					+ IOHelperAgent.FILE_SEPARATOR
					+ IOHelperAgent.CONFIG_FILE_NAME, defaultConfig.toString());

		} catch (JSONException e) {
		}
	}

	public static void writeConfigDefault(JSONObject object) {
		IOHelperAgent.writeFile(
				IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR
						+ IOHelperAgent.CONFIG_FILE_NAME, object.toString());

	}

	public static void writeRegisterFile(JSONObject registration) {
		JSONObject config = loadConfig();
		
		try {
			if(!registration.has(JSONKeys.REG_RESPONSE_AGENT_ID)) {
				throw new JSONException("");
			}
					
			config.put(JSONKeys.REG_RESPONSE_AGENT_ENABLED,
					registration.get(JSONKeys.REG_RESPONSE_AGENT_ENABLED));
			config.put(JSONKeys.REG_RESPONSE_AGENT_ID,
					registration.get(JSONKeys.REG_RESPONSE_AGENT_ID));
			config.put(JSONKeys.REG_RESPONSE_AGENT_LOG_LEVEL,
					registration.get(JSONKeys.REG_RESPONSE_AGENT_LOG_LEVEL));
			config.put(JSONKeys.REG_RESPONSE_CONFIGURATION_GROUPS, registration
					.get(JSONKeys.REG_RESPONSE_CONFIGURATION_GROUPS));
			config.put(JSONKeys.REG_RESPONSE_DEVICE_ENABLED,
					registration.get(JSONKeys.REG_RESPONSE_DEVICE_ENABLED));
			config.put(JSONKeys.REG_RESPONSE_REALTIME_DATA_ENDPOINT,
					registration
							.get(JSONKeys.REG_RESPONSE_REALTIME_DATA_ENDPOINT));
			config.put(JSONKeys.REG_RESPONSE_SERVER_TIME_EPOCH,
					registration.get(JSONKeys.REG_RESPONSE_SERVER_TIME_EPOCH));
			config.put(JSONKeys.REG_RESPONSE_SESSION_DATA_ENDPOINT,
					registration
							.get(JSONKeys.REG_RESPONSE_SESSION_DATA_ENDPOINT));
			handleOffSet(config, config.get(JSONKeys.REG_RESPONSE_SERVER_TIME_EPOCH).toString(), System.currentTimeMillis());

			IOHelperAgent.writeFile(IOHelperConfigAgent.FILE_PATH
					+ IOHelperAgent.FILE_SEPARATOR
					+ IOHelperAgent.CONFIG_FILE_NAME, config.toString());
		} catch (JSONException e) {
			try {
				config.put(JSONKeys.REG_RESPONSE_AGENT_ENABLED,
						registration.get(JSONKeys.REG_RESPONSE_AGENT_ENABLED));
				config.put(JSONKeys.REG_RESPONSE_SESSION_DATA_ENDPOINT,
						registration
								.get(JSONKeys.REG_RESPONSE_SESSION_DATA_ENDPOINT));
				config.put(JSONKeys.REG_RESPONSE_REALTIME_DATA_ENDPOINT,
						registration
								.get(JSONKeys.REG_RESPONSE_REALTIME_DATA_ENDPOINT));
				config.put(JSONKeys.REG_RESPONSE_DEVICE_ENABLED,
						registration.get(JSONKeys.REG_RESPONSE_DEVICE_ENABLED));
				config.put(JSONKeys.REG_RESPONSE_AGENT_LOG_LEVEL,
						registration.get(JSONKeys.REG_RESPONSE_AGENT_LOG_LEVEL));
				config.put(JSONKeys.REG_RESPONSE_CONFIGURATION_GROUPS, registration
						.get(JSONKeys.REG_RESPONSE_CONFIGURATION_GROUPS));
				config.put(JSONKeys.REG_RESPONSE_SERVER_TIME_EPOCH,
						registration.get(JSONKeys.REG_RESPONSE_SERVER_TIME_EPOCH));
				handleOffSet(config, config.get(JSONKeys.REG_RESPONSE_SERVER_TIME_EPOCH).toString(), System.currentTimeMillis());
			
				IOHelperAgent.writeFile(IOHelperConfigAgent.FILE_PATH
						+ IOHelperAgent.FILE_SEPARATOR
						+ IOHelperAgent.CONFIG_FILE_NAME, config.toString());
				
			} catch (JSONException e1) {
			}
		}
	}

	private static void handleOffSet(JSONObject config, String offsetValue,
			long currentTimeMilliseconds) throws JSONException {
		long deviceTimeSeconds = currentTimeMilliseconds / 1000;
		long serverTimeSeconds = Long.parseLong(offsetValue);
		long offset = serverTimeSeconds - deviceTimeSeconds;
		if (Math.abs(offset) > 30) {
			config.put(HelperConfigAgent.SERVER_TIME_EPOCH_OFFSET,
					Long.toString(offset));
		} else {
			config.put(HelperConfigAgent.SERVER_TIME_EPOCH_OFFSET, "0");
		}
	}

	public static JSONObject loadConfig() {
		try {
			String str = IOHelperAgent.readFile(IOHelperConfigAgent.FILE_PATH
					+ IOHelperAgent.FILE_SEPARATOR
					+ IOHelperAgent.CONFIG_FILE_NAME);
			if (str == null) {
				return null;
			} else {
				return new JSONObject(str);
			}
		} catch (JSONException e) {
			return null;
		}
	}

	public static JSONObject registerAgent() {
		try {
			JSONObject config = loadConfig();
			String agentId = config.getString(JSONKeys.REG_RESPONSE_AGENT_ID);

			JSONObject registration = UtilConnectionMaker.makePutConnection(
					UtilCommunicationConstants.API_URL
							+ UtilCommunicationConstants.SEPARATOR
							+ UtilCommunicationConstants.AGENT
							+ UtilCommunicationConstants.SEPARATOR + agentId,
					createInitialRegisterObject());
			if (registration != null) {
				writeRegisterFile(registration);
				writeGroupsFile(registration);
				updateGroups();
				return loadConfig();
			} else {
				return null;
			}
		} catch (JSONException e) {
			JSONObject registration = UtilConnectionMaker.makePostConnection(
					UtilCommunicationConstants.API_URL
							+ UtilCommunicationConstants.SEPARATOR
							+ UtilCommunicationConstants.AGENT,
					createInitialRegisterObject());
			if (registration != null) {
				writeRegisterFile(registration);
				writeGroupsFile(registration);
				return loadConfig();
			} else {
				return null;
			}
		}
	}

	private static void writeGroupsFile(JSONObject registration) {
		try {
			JSONArray toUpdateArray = new JSONArray();
			JSONArray groupsNew = null;
			if(registration.has(JSONKeys.REG_RESPONSE_CONFIGURATION_GROUPS)) {
				groupsNew = (JSONArray) registration.get(JSONKeys.REG_RESPONSE_CONFIGURATION_GROUPS);
			}
			
			if(groupsNew != null) {
				String groupsOldString = IOHelperAgent.readFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR + IOHelperAgent.GROUPS_FILE_NAME);
				if(groupsOldString != null) {
					JSONArray groupsOld = new JSONArray(groupsOldString);
			
					for (int i = 0 ; i < groupsNew.length() ; i++) {
						JSONObject toUpdate = compareGroupVersions((JSONObject) groupsNew.get(i), groupsOld);
						if(toUpdate != null) {
							toUpdateArray.put(toUpdate);
						}
					}
			
					// write a file containing the groups that need updating
					if(toUpdateArray.length() > 0) {
						IOHelperAgent.writeFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR + IOHelperAgent.GROUPS_TO_UPDATE_FILE_NAME, toUpdateArray.toString());
					}
				} else {
					IOHelperAgent.writeFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR + IOHelperAgent.GROUPS_TO_UPDATE_FILE_NAME, groupsNew.toString());
				}
				
				IOHelperAgent.writeFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR + IOHelperAgent.GROUPS_FILE_NAME, groupsNew.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private static JSONObject compareGroupVersions(JSONObject object, JSONArray groupsOld) {
		try {
			JSONObject toReturn = null;
			int versionNew = Integer.parseInt(object.getString(JSONKeys.GROUPS_VERSION));
			String idNew = object.getString(JSONKeys.GROUPS_ID);
			
			
			for(int i = 0 ; i < groupsOld.length() ; i++) {
				int versionOld = Integer.parseInt(((JSONObject)groupsOld.get(i)).getString(JSONKeys.GROUPS_VERSION));
				String idOld = ((JSONObject)groupsOld.get(i)).getString(JSONKeys.GROUPS_ID);
				if(idNew.equals(idOld)) {
					if(versionNew > versionOld) {
						toReturn = (JSONObject) groupsOld.get(i);
					} else {
						toReturn = null;
					}
				}
			}
			
			return toReturn;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void updateGroups() {
		String groupsArray = IOHelperAgent.readFile(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR +
				IOHelperAgent.GROUPS_TO_UPDATE_FILE_NAME);
		Vector fileNamess = new Vector();
		Vector groupNames = new Vector();
		try {
			JSONObject config = loadConfig();
			String agentId = (String) config.get(JSONKeys.REG_RESPONSE_AGENT_ID);
			if(groupsArray != null) {
				JSONArray groups = new JSONArray(groupsArray);
				for (int i = 0; i < groups.length(); i++) {
					JSONObject groupObject = UtilConnectionMaker
							.makeGetConnection(UtilCommunicationConstants.API_URL
									+ UtilCommunicationConstants.SEPARATOR
									+ UtilCommunicationConstants.APP
									+ UtilCommunicationConstants.SEPARATOR
									+ agentId
									+ UtilCommunicationConstants.SEPARATOR
									+ UtilCommunicationConstants.CONFIGURATION
									+ UtilCommunicationConstants.SEPARATOR
									+ ((JSONObject) groups.get(i))
											.getString(JSONKeys.GROUPS_ID));
					IOHelperAgent
							.writeFile(
									IOHelperAgent.FILE_PATH
											+ IOHelperAgent.FILE_SEPARATOR
											+ IOHelperAgent.GROUPS_FILE_NAME
											+ IOHelperAgent.UNDERSCORE
											+ groupObject
													.getString(JSONKeys.CONF_GROUP_NAME),
									groupObject.toString());
					groupNames.addElement(groupObject
											.getString(JSONKeys.CONF_GROUP_NAME));
					fileNamess.addElement(IOHelperAgent.FILE_PATH
							+ IOHelperAgent.FILE_SEPARATOR
							+ IOHelperAgent.GROUPS_FILE_NAME
							+ IOHelperAgent.UNDERSCORE
							+ groupObject
									.getString(JSONKeys.CONF_GROUP_NAME));
				}
				
				storeFileNames(groupNames, fileNamess);
				
				IOHelperAgent.delete(IOHelperAgent.FILE_PATH + IOHelperAgent.FILE_SEPARATOR +
						IOHelperAgent.GROUPS_TO_UPDATE_FILE_NAME);
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private static void storeFileNames(Vector groupsNames, Vector fileNames) {
		JSONObject json = new JSONObject();
		for(int i = 0 ; i < groupsNames.size() ; i++) {
			try {
				json.put((String) groupsNames.elementAt(i), fileNames.elementAt(i));
				
				IOHelperAgent
				.writeFile(
						IOHelperAgent.FILE_PATH
								+ IOHelperAgent.FILE_SEPARATOR
								+ IOHelperAgent.GROUPS_NAMES_FILE_NAME,
								json.toString());
			} catch (JSONException e) {
			}
		}		
	}

	public static JSONObject loadConfigGroup(String group) {
		try {
			String groups = IOHelperAgent
			.readFile(
					IOHelperAgent.FILE_PATH
							+ IOHelperAgent.FILE_SEPARATOR
							+ IOHelperAgent.GROUPS_FILE_NAME 
							+ IOHelperAgent.UNDERSCORE
							+ group);
			if(groups == null) {
				return null;
			} else {
				return new JSONObject(groups);
			}
		} catch (JSONException e) {
			return null;
		}
	}

	public static JSONObject loadConfigGroups() {
		try {
			String str = IOHelperAgent.readFile(IOHelperAgent.FILE_PATH
								+ IOHelperAgent.FILE_SEPARATOR
								+ IOHelperAgent.GROUPS_NAMES_FILE_NAME);
			if(str == null) {
				return null;
			} else {
				return new JSONObject(str);
			}
		} catch (JSONException e) {
			return null;
		}
	}

}
