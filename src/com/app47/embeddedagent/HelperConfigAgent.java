package com.app47.embeddedagent;

import org.json.JSONException;
import org.json.JSONObject;


public class HelperConfigAgent {

    public static final String SERVER_TIME_EPOCH_OFFSET = "server_time_off";

	//Update Config information from server
    public static void checkForConfigUpdates() {
    	ServiceConfigAgent.handleRequest(ServiceConfigAgent.REQ_UPDATE, null);
    }

    public static boolean agentIsEnabled() {
        JSONObject config = IOHelperConfigAgent.loadConfig();
        try {
			return (config != null && config.getString(JSONKeys.REG_RESPONSE_AGENT_ENABLED) != null && (
			        config.getString(JSONKeys.REG_RESPONSE_AGENT_ENABLED).equalsIgnoreCase("1") ||
			                config.getString(JSONKeys.REG_RESPONSE_AGENT_ENABLED).equalsIgnoreCase("true") ||
			                config.getString(JSONKeys.REG_RESPONSE_AGENT_ENABLED).equalsIgnoreCase("yes")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return false;
    }

    public static JSONObject getConfigGroupItem(String groupName, String key){
      JSONObject configGroup = IOHelperConfigAgent.loadConfigGroup(groupName);
      if(configGroup == null)
        return null;
      try{
    	  return configGroup.getJSONObject(key);
      }catch(JSONException e){
    	  return null;
      }
    }

}
