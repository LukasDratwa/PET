package experimente.yassine;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class RSA_ECB_PAD extends Algorithm{
		
	public RSA_ECB_PAD(){
		try{
			// declaring correct ciphers
			encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public long encryptDecrypt(String message) {
		// initializing key
		KeyPairGenerator keyGen = null;
		SecureRandom random = new SecureRandom();
		try{
			keyGen = KeyPairGenerator.getInstance("RSA");
		} catch(Exception e){
			e.printStackTrace();
		}
		keyGen.initialize(1024, random);
		KeyPair keyPair = keyGen.generateKeyPair();
		
		// save start time, for performance measurement
		long startTime = System.nanoTime();
		
		// encrypt
		byte[] b = encrypt(message, keyPair.getPublic());
		// decrypt
		b = decrypt(b, keyPair.getPrivate());
		
		// check for equality input message and decrypted message
		if(!(new String(b).equals(message))){
			System.err.println("Decrypted message not equals original message.");
			return 0;
		}
		
		// return elapsed time in nano seconds
		return(System.nanoTime() - startTime);
	}
	
	@Override
	public void initDecryptCipher(Key key) {
		try{
	    	decryptCipher.init(Cipher.DECRYPT_MODE, key);
	    } catch(Exception e){
			e.printStackTrace();
		}
	}
}
