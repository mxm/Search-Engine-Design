package postprocessing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

	public static String sha256(String str) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//convert to hexstring
		byte[] diebytes = digest.digest(str.getBytes());
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < diebytes.length; i++) {
			hexString.append(Integer.toHexString(0xFF & diebytes[i]));
		}
		return hexString.toString();
	}

}
