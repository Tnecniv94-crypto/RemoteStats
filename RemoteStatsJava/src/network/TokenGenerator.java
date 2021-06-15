package network;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TokenGenerator {
	public static String generateToken() {
		SecureRandom rand = new SecureRandom();
		MessageDigest digest;
		byte[] b = new byte[8];
		
		try {
			digest = MessageDigest.getInstance("SHA-256");
			rand.nextBytes(b);
			b = digest.digest(b);
			
			return bytesToHex(b).substring(0, 12);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        
	        hexString.append(hex);
	    }
	    
	    return hexString.toString();
	}
}
