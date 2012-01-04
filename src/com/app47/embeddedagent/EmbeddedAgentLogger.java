package com.app47.embeddedagent;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Static class used to make Logging calls to the Embedded Agent
 */
class EmbeddedAgentLogger {

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Debug'
     *
     * @param message your log message
     */
    public static void d(String message) {
        log(message, null, null, "Debug");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Debug'
     *
     * @param message your log message
     */
    public static void debug(String message) {
        d(message);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Debug'
     *
     * @param message your log message
     * @param tags    a vector of tags that will be used to help sort/filter your data
     */
    public static void d(String message, Vector tags) {
        log(message, tags, null, "Debug");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Debug'
     *
     * @param message your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void debug(String message, Vector tags) {
        d(message, tags);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Debug'
     *
     * @param message your log message
     * @param e       a Throwable to add to your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void d(String message, Throwable e, Vector tags) {
        log(message, tags, e, "Debug");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Debug'
     *
     * @param message your log message
     * @param e       a Throwable to add to your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void debug(String message, Throwable e, Vector tags) {
        d(message, e, tags);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Info'
     *
     * @param message your log message
     */
    public static void i(String message) {
        log(message, null, null, "Info");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Info'
     *
     * @param message your log message
     */
    public static void info(String message) {
        i(message);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Info'
     *
     * @param message your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void i(String message, Vector tags) {
        log(message, tags, null, "Info");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Info'
     *
     * @param message your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void info(String message, Vector tags) {
        i(message, tags);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Info'
     *
     * @param message your log message
     * @param e       a Throwable to add to your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void i(String message, Throwable e, Vector tags) {
        log(message, tags, e, "Info");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Info'
     *
     * @param message your log message
     * @param e       a Throwable to add to your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void info(String message, Throwable e, Vector tags) {
        i(message, e, tags);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Error'
     *
     * @param message your log message
     */
    public static void e(String message) {
        log(message, null, null, "Error");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Error'
     *
     * @param message your log message
     */
    public static void error(String message) {
        e(message);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Error'
     *
     * @param message your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void e(String message, Vector tags) {
        log(message, tags, null, "Error");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Error'
     *
     * @param message your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void error(String message, Vector tags) {
        e(message, tags);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Error'
     *
     * @param message your log message
     * @param e       a Throwable to add to your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void e(String message, Throwable e, Vector tags) {
        log(message, tags, e, "Error");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Error'
     *
     * @param message your log message
     * @param e       a Throwable to add to your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void error(String message, Throwable e, Vector tags) {
        e(message, e, tags);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Warn'
     *
     * @param message your log message
     */
    public static void w(String message) {
        log(message, null, null, "Warn");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Warn'
     *
     * @param message your log message
     */
    public static void warn(String message) {
        w(message);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Warn'
     *
     * @param message your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void w(String message, Vector tags) {
        log(message, tags, null, "Warn");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Warn'
     *
     * @param message your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void warn(String message, Vector tags) {
        w(message, tags);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Warn'
     *
     * @param message your log message
     * @param e       a Throwable to add to your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void w(String message, Throwable e, Vector tags) {
        log(message, tags, e, "Warn");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Warn'
     *
     * @param message your log message
     * @param e       a Throwable to add to your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void warn(String message, Throwable e, Vector tags) {
        w(message, e, tags);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Crash'
     *
     * @param message your log message
     */
    public static void wtf(String message) {
        log(message, null, null, "Crash");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Crash'
     *
     * @param message your log message
     */
    public static void crash(String message) {
        wtf(message);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Crash'
     *
     * @param message your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void wtf(String message, Vector tags) {
        log(message, tags, null, "Crash");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Crash'
     *
     * @param message your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void crash(String message, Vector tags) {
        wtf(message, tags);
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Crash'
     *
     * @param message your log message
     * @param e       a Throwable to add to your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void wtf(String message, Throwable e, Vector tags) {
        log(message, tags, e, "Crash");
    }

    /**
     * Submits a Log message to the Embedded Agent with a Log Level of 'Crash'
     *
     * @param message your log message
     * @param e       a Throwable to add to your log message
     * @param tags    a list of tags that will be used to help sort/filter your data
     */
    public static void crash(String message, Throwable e, Vector tags) {
        wtf(message, e, tags);
    }


    private static void log(String message, Vector logTags, Throwable e, String level) {
        JSONObject config = IOHelperConfigAgent.loadConfig();
        String agentLevel = null;
		try {
			agentLevel = config.getString(JSONKeys.REG_RESPONSE_AGENT_LOG_LEVEL);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
        if (shouldNotLog(level, agentLevel)) {
            return;
        }
        long startTime = System.currentTimeMillis();
        String sessionID = IOHelperSessionAgent.loadCurrentSessionStartID();

        //setup message for logging
        String formattedMessage = message;
        if (logTags != null && logTags.size() > 0) {
            formattedMessage += " (tags: [";
            for (int i = 0; i < logTags.size(); i++) {
                formattedMessage += logTags.elementAt(i);
                if (i < logTags.size() - 1) {
                    formattedMessage += ", ";
                }
            }
            formattedMessage += "]) ";
        }

        //Log message to logcat
        if (level.equals("Debug")) {
            if (e != null) {
                System.out.println("D - EATag " + formattedMessage + " " + e);
            } else {
            	System.out.println("D - EATag" +  formattedMessage);
            }
        } else if (level.equals("Info")) {
            if (e != null) {
            	System.out.println("I - EATag" +  formattedMessage + " " + e);
            } else {
            	System.out.println("I - EATag" +  formattedMessage);
            }
        } else if (level.equals("Warn")) {
            if (e != null) {
            	System.out.println("W - EATag" + formattedMessage + " " + e);
            } else {
            	System.out.println("W - EATag" + formattedMessage);
            }
        } else if (level.equals("Error")) {
            if (e != null) {
            	System.out.println("E - EATag" + formattedMessage + " " + e);
            } else {
            	System.out.println("E - EATag" + formattedMessage);
            }
        } else if (level.equals("Crash")) {
            if (e != null) {
            	System.out.println("C - EATag" + formattedMessage +" " + e);
            } else {
            	System.out.println("C - EATag" + formattedMessage);
            }
        }
        //create log object (JSON Object)
        ObjectAgentLog log = null;
        try {
            log = new ObjectAgentLog(config, sessionID, level, message, logTags, e);
        } catch (JSONException err) {
            log = null;
        }

        //add new log to cache (JSON Array)
        if (log != null) {
            JSONArray logs = IOHelperLogAgent.loadLogs();
            logs.put(log);
            IOHelperLogAgent.writeLogs(logs);
        }

        System.out.println("I - EATag EALogger took " + (System.currentTimeMillis() - startTime));
    }

    private static boolean shouldNotLog(String level, String agentLevel) {
        return !HelperConfigAgent.agentIsEnabled() || agentLevel == null || levelToInt(level) < levelToInt(agentLevel);
    }

    private static int levelToInt(String theLevel) {
        String level = theLevel.toLowerCase();
        if (level.equals("debug")) {
            return 1;
        } else if (level.equals("info")) {
            return 2;
        } else if (level.equals("warn")) {
            return 3;
        } else if (level.equals("error")) {
            return 4;
        } else if (level.equals("crash")) {
            return 5;
        } else {
            return 100;
        }
    }
}