package com.app47.embeddedagent;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ServiceEventAgent {
	public static String RESUME = "resume";
	public static String SEND_EVENT = "send_event";
	public static String SEND_GENERIC_EVENT = "send_generic_event";
	public static String FINISH_TIMED = "finish_timed";
	private static ObjectAgentTimedEvent event2;

	public static void handleRequest(String eventString, Vector tag,
			String name, String uuid) {

		// checks to make sure the config exists and has the required values
		JSONObject config = IOHelperConfigAgent.loadConfig();
		if (eventString.equals("resume")) {
			onResumeEventCheck(config);
		} else if (eventString.equals("send_event")) {

			try {
				event2 = new ObjectAgentTimedEvent(IOHelperConfigAgent.loadConfig(),
						name, tag, HelperEnvironmentAgent.getGPS());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			sendEvent(event2, config);
		} else if (eventString.equals(ServiceEventAgent.SEND_GENERIC_EVENT)) {
			ObjectAgentEvent event = buildEvent(config, name, tag);
			sendEvent(event, config);
		} else if (eventString.equals("finish_timed")) {
			finishTimedEvent(tag, config, uuid);
		}

		else {
			System.out.println("Event Service not provided valid type");
		}
	}

	public static String startTimedEvent(String name, Vector tags) {
		if (HelperConfigAgent.agentIsEnabled()) {
			double gps[] = { 33.22, 22.11 };
			try {

				ObjectAgentTimedEvent event = new ObjectAgentTimedEvent(
						IOHelperConfigAgent.loadConfig(), name, tags,
						HelperEnvironmentAgent.getGPS());
				JSONObject events = IOHelperAgent.loadActiveTimedEvents();
				events.put(event.getUuid(), event);
				// events.put(event.getUuid(), event);
				IOHelperAgent.writeActiveTimedEvents(events);

				/*
				 * JSONObject dd = AgentConfigIOHelper.loadConfig();
				 * AgentTimedEvent event = new AgentTimedEvent(
				 * AgentConfigIOHelper.loadConfig(), name, tags, gps); String
				 * events = AgentIOHelper.loadActiveTimedEvents();
				 * 
				 * AgentIOHelper.writeActiveTimedEvents(event.toString());
				 */
				handleRequest(ServiceEventAgent.SEND_EVENT, tags, null, null);
				return event.getUuid();
			} catch (JSONException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	// done !
	private static void onResumeEventCheck(JSONObject config) {
		JSONObject finishedEvents = null;

		try {
			JSONObject jsonObject = IOHelperAgent.loadFinishedEvents();
			String str = jsonObject.toString();
			if (str != null) {
				finishedEvents = new JSONObject(str);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (finishedEvents != null) {
			JSONArray keys = finishedEvents.names();
			if (keys == null) {
				return;
			}
			for (int i = 0; i < keys.length(); i++) {
				try {
					String key = keys.getString(i);
					ObjectAgentEvent event = new ObjectAgentEvent(
							(finishedEvents.getJSONObject(key)));

					boolean ok = UtilConnectionMaker
							.makePostConnectionNoResponse(
									UtilCommunicationConstants.API_URL
											+ UtilCommunicationConstants.SEPARATOR
											+ UtilCommunicationConstants.LOG
											+ UtilCommunicationConstants.SEPARATOR
											+ config.get(JSONKeys.REG_RESPONSE_AGENT_ID),
									finishedEvents);
					if (ok == true) {

					} else {

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			IOHelperAgent.writeFinishedEvents(finishedEvents);
		}
	}

	private static boolean executeRequest(JSONObject config, ObjectAgentEvent event) {

		boolean ok = false;
		try {
			Object ss = config.get(JSONKeys.REG_RESPONSE_AGENT_ID);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (event.has("duration")) {

			try {
				ok = UtilConnectionMaker.makePostConnectionNoResponse(
						UtilCommunicationConstants.API_URL_EVENT
								+ UtilCommunicationConstants.SEPARATOR
								+ UtilCommunicationConstants.SEND_EVENT
								+ UtilCommunicationConstants.SEPARATOR
								+ config.get(JSONKeys.REG_RESPONSE_AGENT_ID),
						event);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(ok);
		} else {
			// response = ConnectionMaker.makeEventRequest(config, event);
			try {
				ok = UtilConnectionMaker.makePostConnectionNoResponse(
						UtilCommunicationConstants.API_URL_EVENT
								+ UtilCommunicationConstants.SEPARATOR
								+ UtilCommunicationConstants.EVENT
								+ UtilCommunicationConstants.SEPARATOR
								+ config.get(JSONKeys.REG_RESPONSE_AGENT_ID),
						event);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// return response;
		return ok;
	}

	private static JSONObject getRequestParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	private static JSONObject getRequestParametersDistance() {

		return null;
	}

	private static ObjectAgentEvent buildEvent(JSONObject config, String eventString,
			Vector tag) {
		try {
			return new ObjectAgentEvent(config, eventString, tag,
					HelperEnvironmentAgent.getGPS());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void finishTimedEvent(Vector tags, JSONObject config,
			String uuid) {
		JSONObject events = null;
		events = IOHelperAgent.loadActiveTimedEvents();
		ObjectAgentTimedEvent event;
		try {
			event = new ObjectAgentTimedEvent(events.getJSONObject(uuid));
		} catch (JSONException e) {
			event = null;
		}

		if (event == null) {
		} else {
			// add the new tags
			// setup message for logging
			/*
			 * String formattedMessage = null; if (tags != null && tags.size() >
			 * 0) { formattedMessage += " (tags: ["; for (int i = 0; i <
			 * tags.size(); i++) { formattedMessage += tags.elementAt(i); if (i
			 * < tags.size() - 1) { formattedMessage += ", "; } }
			 * formattedMessage += "]) "; }
			 */
			if (tags != null) {
				event.addTags(tags);
			}
			// event.addTags(addTags)

			event.finish();
			// remove the event from the active timed events, event is finished
			events.remove(uuid);
			IOHelperAgent.writeActiveTimedEvents(events);
			sendEvent(event, config);
		}
	}

	private static void sendEvent(ObjectAgentEvent event, JSONObject config) {
		// TODO:make the connection here
		boolean response = executeRequest(config, event);

		if (response) { // connection // failed

			// add log to finished cache
			JSONObject finishedEvents = IOHelperAgent.loadFinishedEvents();
			try {
				finishedEvents.put(event.getUuid(), event);
				IOHelperAgent.writeFinishedEvents(finishedEvents);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		System.out.println();

	}

	/*
	 * public static String startTimedEvent(String name, Vector tags) { // if
	 * (AgentConfigHelper.agentIsEnabled(c)) { double gps[] = { 33.22, 22.11 };
	 * try { AgentTimedEvent event = new AgentTimedEvent(
	 * AgentConfigIOHelper.loadConfig(), name, tags, gps); String events =
	 * AgentIOHelper.loadActiveTimedEvents();
	 * 
	 * AgentIOHelper.writeActiveTimedEvents(events); return event.getUuid(); }
	 * catch (JSONException e) { return null; }
	 * 
	 * }
	 */
}
