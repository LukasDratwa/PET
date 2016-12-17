package experimente.yassine;

import java.security.*;

public class YassineMain {
	public static void main(String[] args) throws NoSuchAlgorithmException{
		String plainText = "Hello World!";
		RSA_ECB_PAD rsa = new RSA_ECB_PAD();
		AES_CBC_PAD aes = new AES_CBC_PAD();
		System.out.println("RSA: " + rsa.encryptDecrypt(plainText));
		System.out.println("AES: " + aes.encryptDecrypt(plainText));
	}
}
