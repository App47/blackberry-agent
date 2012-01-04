package com.app47.embeddedagent;


public class HelperSessionAgent {
	public static final long SESSION_ACTIVITY_DELAY = 5000;
	// Broadcast Actions for Test Apps
	public static final String SESSION_BROADCAST_ACTION = "com.app47.embeddedagent.SESSIONS_UPDATED";
	public static final String LOGGING_BROADCAST_ACTION = "com.app47.embeddedagent.LOGGING_UPDATED";

	public static void onResumeSessionCheck() {
		if (HelperConfigAgent.agentIsEnabled()) {
			// TODO on resume event check
			ServiceSessionAgent.handleRequest(ServiceSessionAgent.RESUME);
		}
	}

	public static void onPauseSessionCheck() {
		if (HelperConfigAgent.agentIsEnabled()) {
			ServiceSessionAgent.handleRequest(ServiceSessionAgent.PAUSE);
		}

	}

	public static String generateUUID() {
		return String.valueOf(net.rim.device.api.synchronization.UIDGenerator
				.getUID());
	}

}
