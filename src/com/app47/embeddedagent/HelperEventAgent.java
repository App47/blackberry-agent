package com.app47.embeddedagent;


public class HelperEventAgent {

	// Broadcast Actions for Test Apps
	public static final String ACTIVE_TIMED_EVENT_ACTION = "com.app47.embeddedagent.ACTIVE_TIMED_EVENTS_UPDATED";
	public static final String FINISHED_EVENT_ACTION = "com.app47.embeddedagent.FINISHED_TIMED_EVENTS_UPDATED";

	/*
	 * public static String startTimedEvent(String name, Vector tags) { if
	 * (AgentConfigHelper.agentIsEnabled()) { double gps[] = { 33.22, 22.11 };
	 * try { JSONObject dd = AgentConfigIOHelper.loadConfig(); AgentTimedEvent
	 * event = new AgentTimedEvent( AgentConfigIOHelper.loadConfig(), name,
	 * tags, gps); String events = AgentIOHelper.loadActiveTimedEvents();
	 * 
	 * AgentIOHelper.writeActiveTimedEvents(events);
	 * AgentEventService.handleRequest(AgentEventService.SEND_EVENT, tags,
	 * null); return event.getUuid(); } catch (JSONException e) { return null; }
	 * } return null;
	 * 
	 * }
	 */
}
