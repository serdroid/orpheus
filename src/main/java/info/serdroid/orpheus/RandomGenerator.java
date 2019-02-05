package info.serdroid.orpheus;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomGenerator {
	static SecureRandom secureRandom = new SecureRandom();

	public static String generateRandomString() {
		BigInteger randomInt =  new BigInteger(130, secureRandom);
		return randomInt.toString(32);
	}
	
}
