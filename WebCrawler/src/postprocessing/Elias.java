package postprocessing;

public class Elias {

	/*
	 * Elias.java Implementation of the Elias delta encoding
	 * 
	 * 
	 * According to http://en.wikipedia.org/wiki/Elias_delta_coding
	 * 
	 * 2011 - Maximilian Michels max.michels@fu-berlin.de
	 */

	private static String convertToBinaryString(int num) {
		String str = "";

		if (num == 0)
			return "0";

		while (num > 0) {
			str = num % 2 + str;
			num = num / 2;
		}
		return str;
	}

	private static int convertBinaryStringToNum(String str) {

		int num = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '1') {
				num += (int) Math.pow(2, str.length() - i - 1);
			}
		}
		return num;
	}

	public static String deltaEncode(int num) {
		// first we convert to binary
		String bin = convertToBinaryString(num);
		// we take the number of bits of bin and convert to binary
		String bitsInBinary = convertToBinaryString(bin.length());
		// we remve the trailing bit from bin
		String removeRemainingBit = bin.substring(1);
		// we combine the two
		String combined = bitsInBinary + removeRemainingBit;
		// we prepend bitsInBinary -1 many 0 bits
		for (int i = 0; i < bitsInBinary.length() - 1; i++) {
			combined = "0" + combined;
		}
		return combined;
	}

	public static int deltaDecode(String str) {

		// count the number of 0s at the beginning of string
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '0')
				count++;
			else
				break;
		}
		// the next count bytes from the 1 determine the number of bits to read
		// afterwards
		String temp = "";
		for (int i = count; i <= 2 * count; i++) {
			char c = str.charAt(i);
			temp += c;
		}
		int numOfBitsToRead = convertBinaryStringToNum(temp);

		// we read the remaining bits which form the number
		String output = "1";
		for (int i = 2 * count + 1; i < 2 * count + numOfBitsToRead; i++) {
			char c = str.charAt(i);
			output += c;
		}

		return convertBinaryStringToNum(output);
	}

	public static void main(String[] args) {
		System.out.println(deltaEncode(456));
		System.out.println(deltaDecode(deltaEncode(456)));
	}

}
