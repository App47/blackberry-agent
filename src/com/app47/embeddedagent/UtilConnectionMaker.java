package com.app47.embeddedagent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import org.json.JSONException;
import org.json.JSONObject;


public class UtilConnectionMaker {
	private static final String GET = HttpConnection.GET;
	private static final String POST = HttpConnection.POST;
	private static final String PUT = "PUT";

	public static JSONObject makeGetConnection(String url) {
		return makeConnection(url, null, GET);
	}

	public static JSONObject makePostConnection(String url,
			JSONObject postObject) {
		return makeConnection(url, postObject, POST);
	}

	public static JSONObject makePutConnection(String url, JSONObject putObject) {
		return makeConnection(url, putObject, PUT);
	}

	private static JSONObject makeConnection(String url, JSONObject postObject, String connType) {
		HttpConnection c = null;
		OutputStream os = null;
		InputStream is = null;
		StringBuffer sb = new StringBuffer();
		try {				
			c = (HttpConnection) Connector.open(url + UtilNetworkUtility.getConnectionSuffix());

			// Set the request method and headers
			c.setRequestMethod(connType);
			c.setRequestProperty("Content-Type","application/json");
			c.setRequestProperty("Content-Encoding", "UTF-8");
			c.setRequestProperty("Connection", "Keep-Alive");
			c.setRequestProperty("User-Agent","Profile/MIDP-2.0 Configuration/CLDC-1.0");
			
			if(postObject != null) {
				String input = postObject.toString();
				c.setRequestProperty("Content-Length", Integer.toString(input.getBytes().length));
				// Getting the output stream may flush the headers
				os = c.openOutputStream();

				// os.write(xmlInventory.toString().getBytes());
				os.write(input.getBytes("UTF-8"));
				os.flush(); // Optional, getResponseCode will flush
			}
			
			// Getting the response code will open the connection,
			// send the request, and read the HTTP response headers.
			// The headers are stored until requested.
			final int rc = c.getResponseCode();
			if(rc == HttpConnection.HTTP_OK) {
				is = c.openInputStream();
				InputStreamReader isReader = new InputStreamReader(is);
				char i;
				int j = 0;
				while( (j = isReader.read()) != -1) {
					i = (char)j;
					sb.append(i);
				}
				
				return new JSONObject(sb.toString()).getJSONObject("data");
			} else {
				return null;
			}
		} catch (JSONException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public static boolean makePostStringConnection(String url, String string) {
		HttpConnection c = null;
		OutputStream os = null;
		InputStream is = null;
		StringBuffer sb = new StringBuffer();
		try {
			c = (HttpConnection) Connector.open(url + UtilNetworkUtility.getConnectionSuffix());

			// Set the request method and headers
			c.setRequestMethod(HttpConnection.POST);
			c.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			c.setRequestProperty("Content-Encoding", "UTF-8");
			c.setRequestProperty("Connection", "Keep-Alive");
			c.setRequestProperty("User-Agent",
					"Profile/MIDP-2.0 Configuration/CLDC-1.0");

			String input = string;
			c.setRequestProperty("Content-Length",
					Integer.toString(input.getBytes().length));
			// Getting the output stream may flush the headers
			os = c.openOutputStream();

			// os.write(xmlInventory.toString().getBytes());
			os.write(input.getBytes("UTF-8"));
			os.flush(); // Optional, getResponseCode will flush

			// Getting the response code will open the connection,
			// send the request, and read the HTTP response headers.
			// The headers are stored until requested.
			final int rc = c.getResponseCode();
			if (rc == HttpConnection.HTTP_OK) {
				is = c.openInputStream();
				InputStreamReader isReader = new InputStreamReader(is);
				char i;
				int j = 0;
				while ((j = isReader.read()) != -1) {
					i = (char) j;
					sb.append(i);
				}

				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean makePostConnectionNoResponse(String string,
			JSONObject obj) {
		HttpConnection c = null;
		OutputStream os = null;
		InputStream is = null;
		StringBuffer sb = new StringBuffer();
		try {
			c = (HttpConnection) Connector.open(string + UtilNetworkUtility.getConnectionSuffix());

			// Set the request method and headers
			c.setRequestMethod(HttpConnection.POST);
			c.setRequestProperty("Content-Type", "application/json");
			c.setRequestProperty("Content-Encoding", "UTF-8");
			c.setRequestProperty("Connection", "Keep-Alive");
			c.setRequestProperty("User-Agent",
					"Profile/MIDP-2.0 Configuration/CLDC-1.0");

			if (obj != null) {
				String input = obj.toString();
				c.setRequestProperty("Content-Length",
						Integer.toString(input.getBytes().length));
				// Getting the output stream may flush the headers
				os = c.openOutputStream();

				// os.write(xmlInventory.toString().getBytes());
				os.write(input.getBytes("UTF-8"));
				os.flush(); // Optional, getResponseCode will flush
			}

			// Getting the response code will open the connection,
			// send the request, and read the HTTP response headers.
			// The headers are stored until requested.
			final int rc = c.getResponseCode();
			if (rc == HttpConnection.HTTP_OK) {
				is = c.openInputStream();
				InputStreamReader isReader = new InputStreamReader(is);
				char i;
				int j = 0;
				while ((j = isReader.read()) != -1) {
					i = (char) j;
					sb.append(i);
				}

				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}
	
	public static boolean makeTimedEventRequest(JSONObject config, String url) {
		return makePostConnectionNoResponse(url, config);
	}

	public static boolean makeEventRequest(JSONObject config, String url) {
		return makePostConnectionNoResponse(url, config);
	}
}
