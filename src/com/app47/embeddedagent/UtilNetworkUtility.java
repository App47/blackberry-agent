package com.app47.embeddedagent;

import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.RadioInfo;
import net.rim.device.api.system.WLANInfo;
/**
 * This class provides network utilities
 * @author bogdan.sara
 *
 */
class UtilNetworkUtility {
	
	public static String getConnectionSuffix() {

		String connSuffix = null;
		
		if (DeviceInfo.isSimulator()) {
			connSuffix = ";deviceside=false";
		} else if ((WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED) 
				&& RadioInfo.areWAFsSupported(RadioInfo.WAF_WLAN)) {
			connSuffix = ";interface=wifi";
		} else if(CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_MDS)) {
			connSuffix = ";deviceside=false";
		} else {
			String uid = null;
			ServiceBook sb = ServiceBook.getSB();
			ServiceRecord[] records = sb.findRecordsByCid("WPTCP");
			for (int i = 0; i < records.length; i++) {
				if (records[i].isValid() && !records[i].isDisabled()) {
					if (records[i].getUid() != null
							&& records[i].getUid().length() != 0) {
						if ((records[i].getCid().toLowerCase().indexOf("wptcp") != -1)
								&& (records[i].getUid().toLowerCase().indexOf(
										"wifi") == -1)
								&& (records[i].getUid().toLowerCase().indexOf(
										"mms") == -1)) {
							uid = records[i].getUid();
							break;
						}
					}
				}
			}
			if (uid != null) {
				// WAP2 Connection
				connSuffix = ";ConnectionUID=" + uid;
			}
			else {
				connSuffix = ";deviceside=true";
			}
		}
		return connSuffix;
	}
	
}
