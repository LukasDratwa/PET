package experimente.yassine;

import javax.crypto.Cipher;
import java.security.Key;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.security.SecureRandom;
import javax.crypto.spec.IvParameterSpec;

public class AES_CBC_PAD extends Algorithm{

	public AES_CBC_PAD(){	
		try{
			// declaring correct ciphers
			encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public long encryptDecrypt(String message) {	
		// initializing key
		KeyGenerator keyGen = null;
		SecureRandom random = new SecureRandom();
		try{
			keyGen = KeyGenerator.getInstance("AES");
		} catch(Exception e){
			e.printStackTrace();
		}
		keyGen.init(128, random);
		SecretKey key = keyGen.generateKey();
		
		// save start time, for performance measurement
		long startTime = System.nanoTime();
		
		// encrypt
		byte[] b = encrypt(message, key);
		// decrypt
		b = decrypt(b, key);
		
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
		IvParameterSpec ivParameterSpec = new IvParameterSpec(encryptCipher.getIV());
	    try{
	    	decryptCipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
	    } catch(Exception e){
			e.printStackTrace();
		}
	}
}
