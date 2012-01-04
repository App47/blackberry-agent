package com.app47.embeddedagent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import net.rim.device.api.crypto.BlockEncryptor;
import net.rim.device.api.crypto.CryptoTokenException;
import net.rim.device.api.crypto.CryptoUnsupportedOperationException;
import net.rim.device.api.crypto.PKCS5FormatterEngine;
import net.rim.device.api.crypto.TripleDESEncryptorEngine;
import net.rim.device.api.crypto.TripleDESKey;


public class HelperEnvironmentAgent {

    public static String getAppVersion() {
            return UtilAgentConstants.agentVersion;
    }

    public static String longToDate(long input) {
        
        TimeZone tz = TimeZone.getDefault();
        
//        Wdy, DD-Mon-YYYY HHMMSS GMT
//        yyyy-MM-dd hh:mm:ss:SSSS z
//        Wdy, DD Mon YYYY HH:MM:SS TZD
//        YYYY-MM-DDThh:mm:ss.sTZD
        
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.valueOf(input + tz.getRawOffset());
    }

    public static String getDeviceID(JSONObject config) throws JSONException {
        String id = UtilAgentConstants.devicePIN;

        String rawBool = config.getString(JSONKeys.SHOW_IDENTIFIER);
        if (rawBool == null || rawBool.toLowerCase().equals("no") || rawBool.toLowerCase().equals("false") || rawBool.toLowerCase().equals("0")) {
            try {
            	 TripleDESKey _key = new TripleDESKey();
                 TripleDESEncryptorEngine encryptionEngine = 
                     new TripleDESEncryptorEngine(_key);

                 // In most cases, the data to encrypt will not fit into the block
                 // length of a cipher. When that happens, use a padding algorithm
                 // to pad out the last block. This uses PKCS5 to do the padding.
                 PKCS5FormatterEngine formatterEngine = new PKCS5FormatterEngine( 
                       encryptionEngine );

                 // Use the byte array output stream to catch the encrypted information.
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                 // Create a block encryptor to help use the triple DES engine.
                 BlockEncryptor encryptor = new BlockEncryptor( formatterEngine, 
                       outputStream );

                 // Encrypt the data.
                 encryptor.write( id.getBytes() );

                 // Close the stream. This forces the extra bytes to be padded out if 
                 // there were not enough bytes to fill all of the blocks.
                 encryptor.close();

                 // Get the encrypted data.
                 byte[] encryptedData = outputStream.toByteArray();
                 id = new String(encryptedData);
            } catch (CryptoTokenException e) {
				e.printStackTrace();
			} catch (CryptoUnsupportedOperationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        if (id != null) {
            int lastPosition = id.length() - 1;
            if (id.charAt(lastPosition) == '\n') {  //remove carriage return
                id = id.substring(0, lastPosition);
            }
            return id;
        } else {
            return "No Device ID";
        }
    }

    public static String getDeviceCapacity() {
        return UtilAgentConstants.getDeviceCapacity();
    }

    public static double[] getGPS() {
        
        return null;//gps;
    }

	public void getLocation(double d, double e, float f) {
		
	}

	public void getLocationFailed() {
		
	}

}
