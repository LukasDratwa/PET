package experimente.yassine;

import java.io.ByteArrayOutputStream;
import java.security.*;
import javax.crypto.*;

public abstract class Algorithm {
	
	Cipher encryptCipher;
	Cipher decryptCipher;
	
	// encrypts a message and returns the cipher
	public byte[] encrypt(String message, Key key) {
		byte[] cipherText = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	  
		// initializing output stream and cipher, encrypt afterwards
		try{
			CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);
		    // Encrypt
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			cipherOutputStream.write(message.getBytes());
		    cipherOutputStream.flush();
		    cipherOutputStream.close();  
		} catch(Exception e){
			e.printStackTrace();
		}
		
		cipherText = outputStream.toByteArray();
		return cipherText;
	}
	
	// decrypts a message and returns the plan text
	public byte[] decrypt(byte[] cipherText, Key key) {
		byte[] plainText = null;
		try{
		    // Decrypt
			initDecryptCipher(key);
			plainText = decryptCipher.doFinal(cipherText);
		} catch(Exception e){
			e.printStackTrace();
		}
		return plainText;
	}
	
	// encrypts and decrypts, returns elapsed time for both operations
	public abstract long encryptDecrypt(String message);
	
	// initialize decryption cipher
	public abstract void initDecryptCipher(Key key);
}
