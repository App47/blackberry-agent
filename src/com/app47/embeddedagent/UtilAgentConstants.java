package com.app47.embeddedagent;

import java.io.IOException;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.DeviceInfo;

class UtilAgentConstants {
	
	public static final String DEFAULT_UPDATE_FRQ = "0.1";
	public static final String DEFAULT_UPLOAD_DELAY = "5";
	public static final String DEFAILT_SEND_IDENTIFIER = "false";
	
	public static final String appID = "4e552727a2f8fd000100006f";
	public static final String URL = "http://api-staging.fokl.mobi/agent";
	public static final String manufacturer = "Blackberry";
	public static final String appVersion = ApplicationDescriptor.currentApplicationDescriptor().getVersion();
	public static final String agentVersion = "1.10.2";
	//TODO server handling for BB
	public static final String os = "Android";
	public static final String osVersion = DeviceInfo.getSoftwareVersion();
	public static final String deviceName = DeviceInfo.getDeviceName();
	public static String deviceCapability;
	
	public static String getDeviceCapacity(){
		if(DeviceInfo.isSimulator()) {
			return "Simulator";
		} else {
			String root = null;
			Enumeration e = FileSystemRegistry.listRoots();
			while (e.hasMoreElements()) {
				 String path;
				 FileConnection fc;
				 try {
					 root = (String) e.nextElement();
					 if( root.equalsIgnoreCase("sdcard/") ) {
						fc = (FileConnection) Connector.open("file:///SDCard");
						return "" + fc.availableSize();
					 } else if( root.equalsIgnoreCase("store/") ) {
						return "Device";
					 }
				 } catch (IOException e1) {}
			}
			return "Unknown";
		}
		
	}
	
	public static final String devicePIN = "" + DeviceInfo.getDeviceId();
}
