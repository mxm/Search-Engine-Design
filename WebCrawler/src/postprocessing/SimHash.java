package postprocessing;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SimHash {

	/*
	 * Simhash.java
	 * Calculats the SimHash for a given String
	 * 
	 * 2011 - Maximilian Michels
	 * max.michels@fu-berlin.de
	 */

	public static String hash(String str) throws UnsupportedEncodingException {

		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");

			/* MD5 has 16 bytes = 128 bit */
			int[] weightVector = new int[128];

			String[] words = str.split(" ");

			for (String word : words) {

				digest.update(word.getBytes("utf-8"));
				byte[] hash = digest.digest();

				/*
				 * get bits of every byte of the hash and add them to the weight
				 * Vector
				 */
				for (int j = 0; j < hash.length; j++) {
					for (int k = 0; k < 8; k++) {
						if ((hash[j] >> (7 - k) & 0x01) == 1)
							weightVector[j * 8 + k] += 1;
						else
							weightVector[j * 8 + k] -= 1;
					}
				}
			}

			// 128 bits = 16 bytes
			byte[] result = new byte[16];
			/*
			 * Convert weightVector to hash number by setting every bit >0 to 1
			 * and all the others to 0
			 */
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < 8; j++) {
					if (weightVector[i * 8 + j] > 0) {
						result[i] |= 1 << (7 - j);
					}
				}
			}

			StringBuilder out = new StringBuilder(128);

			for (int i : weightVector) {
				if (i > 0) {
					out.append('1');
				} else {
					out.append('0');

				}
			}

			return out.toString();
			
		} catch (NoSuchAlgorithmException e) {
			return "";
		}

	}
	
	public static int similarity(String hash1, String hash2){
		
		if(hash1.length() != hash2.length())
			return -1;
		
		int common = 0;
		for (int i = 0; i < hash1.length(); i++) {
			if (hash1.charAt(i) == hash2.charAt(i)) {
				common++;
			}
		}
		return (int) (common / 128.0 * 100 + 0.5);
	}

	public static void main(String[] args) throws UnsupportedEncodingException  {

		String str1 = "<html>" + "<body>"
				+ "Hello, this is a unique text. I hope nobody steals it."
				+ "Anyways, have a nice day" + "Max" + "</body>" + "</html>";

		String str2 = "<html>" + "<body>"
				+ "Hello, this is a unique text. I hope nobody steals it."
				+ "Anyways, have a nice day" + "Max" + "</body>" + "</html>";

		String hash1 = hash(str1);
		String hash2 = hash(str2);
		System.out.println(hash1);
		System.out.println(hash2);

		int diff = 0;

		for (int i = 0; i < hash1.length(); i++) {
			if (hash1.charAt(i) != hash2.charAt(i)) {
				diff++;
			}
		}

		System.out.println("Document 1 and 2 differ by "
				+ (int) (diff / 128.0 * 100 + 0.5) + "%");
	}

}
