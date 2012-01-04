package com.app47.embeddedagent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import org.json.JSONException;
import org.json.JSONObject;


class IOHelperAgent {

	public static final String FILE_SEPARATOR = "/";
	public static final String FILE_PATH = "file:///store/home/embeddedAgent";
	public static final String CONFIG_FILE_NAME = "config.properties";
	public static final String GROUPS_FILE_NAME = "groups";
	public static final String GROUPS_TO_UPDATE_FILE_NAME = "groupsUpdate";
	public static final String UNDERSCORE = "_";
	public static final String GROUPS_NAMES_FILE_NAME = "groupsStored";

	static {
		createDirectory();
	}

	protected static FileConnection fc = null;
	protected static DataOutputStream os = null;
	private static DataInputStream is = null;
	protected String fileURL;

	protected final static void closeFile() {
		if (fc.isOpen()) {
			try {
				fc.close();
			} catch (IOException e) {
			}
		}
	}

	public static void delete(String fileName) {
		try {
			fc = (FileConnection) Connector.open(fileName);

			if (fc.exists()) {
				fc.delete();
			}
		} catch (IOException e) {
		} finally {
			closeFile();
		}
	}

	public static String readFile(String fileName) {
		try {
			fc = (FileConnection) Connector.open(fileName);

			if (fc.exists()) {
				is = fc.openDataInputStream();
				return is.readUTF();
			} else
				return null;
		} catch (IOException e) {
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			closeFile();
		}
	}

	private static void createDirectory() {
		try {
			fc = (FileConnection) Connector.open(FILE_PATH + FILE_SEPARATOR,
					Connector.READ_WRITE);

			if (!fc.exists()) {
				fc.mkdir();
			}
		} catch (IOException e) {
		} finally {
			closeFile();
		}
	}

	public static void writeFile(String fileName, String data) {
		writeConfigFile(fileName, data);
	}

	public static void writeFile(String fileName, String event, String data) {
		writeConfigFile(fileName, data);
	}

	private static void writeConfigFile(String fileName, String data) {

		try {
			fc = (FileConnection) Connector.open(fileName);

			// if file exist delete it for config update
			if (fc.exists())
				fc.delete();
			if (!fc.exists())
				fc.create();

			os = fc.openDataOutputStream();
			os.writeUTF(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
			closeFile();
		}
	}

	public static void writeActiveTimedEvents(JSONObject data) {
		writeFile(IOHelperConfigAgent.FILE_PATH + FILE_SEPARATOR
				+ IOHelperAgent.CONFIG_FILE_NAME
				+ "EmbeddedAgentActiveTimedEvents", data.toString());
	}

	public static void writeFinishedEvents(JSONObject events) {
		writeFile(IOHelperConfigAgent.FILE_PATH + FILE_SEPARATOR
				+ IOHelperAgent.CONFIG_FILE_NAME
				+ "EmbeddedAgentFinishedTimedEvents", events.toString());
	}

	public static JSONObject loadActiveTimedEvents() {

		return loadEvents(IOHelperConfigAgent.FILE_PATH + FILE_SEPARATOR
				+ IOHelperAgent.CONFIG_FILE_NAME
				+ "EmbeddedAgentActiveTimedEvents");

	}

	private static JSONObject loadEvents(String type) {
		String str = (String) readFile(type);
		JSONObject events;
		try {
			if (str == null) {
				events = new JSONObject();
			} else {
				events = new JSONObject(str);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			events = new JSONObject();
		}
		return events;
	}

	public static JSONObject loadFinishedEvents() {
		return loadEvents((IOHelperConfigAgent.FILE_PATH + FILE_SEPARATOR
				+ IOHelperAgent.CONFIG_FILE_NAME + "EmbeddedAgentFinishedTimedEvents"));

	}
}
