package com.app47.embeddedagent;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


class ServiceSessionAgent {
	public final static int RESUME = 202;
	public final static int PAUSE = 203;

	public static void handleRequest(int type) {

		// checks to make sure the config exists and has the required values
		if (type == RESUME) {
			try {
				onResumeSessionCheck();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (type == PAUSE) {
			onPauseSessionCheck();
		} else {
			System.out.println("Session Service not provided valid type");
		}

	}

	private static void onResumeSessionCheck() throws JSONException {
		// TODO
		long now = System.currentTimeMillis();

		// check if lastSessionStart is null(firstTime), set it to now if it is
		long lastSessionStart = IOHelperSessionAgent.loadSessionStart();
		if (lastSessionStart == -1) {
			IOHelperSessionAgent.writeSessionStart(now);
			lastSessionStart = now;
		}
		// check if currentSessionUUID is null(firstTime), generate one if it is
		String currentSessionUUID = IOHelperSessionAgent
				.loadCurrentSessionStartID();
		if (currentSessionUUID == null) {
			currentSessionUUID = HelperSessionAgent.generateUUID();
			IOHelperSessionAgent.writeCurrentSessionID(currentSessionUUID);
		}
		// check distance between now and lastPaused
		long lastPaused = IOHelperSessionAgent.loadSessionEnd();
		if (lastPaused != -1
				&& (now - lastPaused) > HelperSessionAgent.SESSION_ACTIVITY_DELAY) {
			// Previous Session is complete

			// create the Session object
			ObjectAgentSession session = new ObjectAgentSession();
			session.setDuration((lastPaused - lastSessionStart) / 1000);
			session.setStartTime(lastSessionStart);

			JSONObject configuration = IOHelperConfigAgent.loadConfig();

			long offset = Long.parseLong(configuration.get(
					HelperConfigAgent.SERVER_TIME_EPOCH_OFFSET).toString());
			long currentTimeSeconds = (lastSessionStart / 1000);
			session.setStartTimeEpochSeconds(currentTimeSeconds + offset);

			// here you need to load the config and
			// 1. make miliseconds to seconds
			// 2. apply offset
			// 3. setStartTimeEpoch

			session.setUuid(currentSessionUUID);
			double[] loc = HelperEnvironmentAgent.getGPS();
			if (loc != null) {
				session.setLocation(loc);
			}
			if (session.getDuration() > 0) {// If negative, app crashed in some
											// way, ignore this session
				// store the session on disk
				Vector sessions = IOHelperSessionAgent.loadSessions();
				if (sessions == null) {
					sessions = new Vector();
				}
				sessions.addElement(session);
				IOHelperSessionAgent.writeSessions(sessions);
			}
			// The next session begins now
			IOHelperSessionAgent.writeSessionStart(now);

			// Generate the next sessionUUID and store it
			currentSessionUUID = HelperSessionAgent.generateUUID();
			IOHelperSessionAgent.writeCurrentSessionID(currentSessionUUID);
		}
		// upload session data
		Vector sessions = IOHelperSessionAgent.loadSessions();
		JSONObject config = IOHelperConfigAgent.loadConfig();
		boolean hasConnection = true;
		if (sessions != null && config != null) {
			int size = sessions.size();
			for (int i = 0; i < size && hasConnection; i++) {
				ObjectAgentSession session = (ObjectAgentSession) sessions.elementAt(i);
				boolean ok = UtilConnectionMaker.makePostStringConnection(
						UtilCommunicationConstants.API_URL
								+ UtilCommunicationConstants.SEPARATOR
								+ UtilCommunicationConstants.SESSION
								+ UtilCommunicationConstants.SEPARATOR
								+ config.get(JSONKeys.REG_RESPONSE_AGENT_ID),
						session.toStringPost((String) config
								.get(JSONKeys.REG_RESPONSE_AGENT_ID)));
				if (!ok) {
					hasConnection = false;
				}
			}

			// re-write the logs to remove logs that have been uploaded
			IOHelperSessionAgent.writeSessions(sessions);

			// upload logs
			if (hasConnection) {// only upload logs if the sessions succeeded
				JSONArray logs = IOHelperLogAgent.loadLogs();
				if (logs != null && logs.length() > 0) {// only upload if there
														// are logs
					JSONObject obj = new JSONObject();
					obj.put(JSONKeys.LOGS, logs);
					boolean ok = UtilConnectionMaker
							.makePostConnectionNoResponse(
									UtilCommunicationConstants.API_URL
											+ UtilCommunicationConstants.SEPARATOR
											+ UtilCommunicationConstants.LOG
											+ UtilCommunicationConstants.SEPARATOR
											+ config.get(JSONKeys.REG_RESPONSE_AGENT_ID),
									obj);
					if (ok) {
						logs = new JSONArray();
					}
					IOHelperLogAgent.writeLogs(logs);
				}
			}
		}
		ServiceEventAgent.handleRequest(ServiceEventAgent.RESUME, null, null,
				null);
	}

	private static void onPauseSessionCheck() {
		IOHelperSessionAgent.writeSessionEnd(System.currentTimeMillis());
		IOHelperAgent.writeActiveTimedEvents(new JSONObject());
	}
}
