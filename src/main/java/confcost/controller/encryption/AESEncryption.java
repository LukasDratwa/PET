package confcost.controller.encryption;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.ke.KeyExchange;
import confcost.network.Frame;
import confcost.util.HexString;

/**
 * Bouncy Castle AES Encryption
 * 
 * @author Marc Eichler
 *
 */
public class AESEncryption extends SymmetricEncryption {

	public AESEncryption(@NonNull KeyExchange keyExchange) {
		super(keyExchange);
	}

	@Override
	public void send(@NonNull Socket socket, int messageLength) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidParameterSpecException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Security.addProvider(new BouncyCastleProvider());

		// Exchange keys
		keyExchange.send(socket);
		
		// Initialize AES Cipher
		Cipher cipher = Cipher.getInstance("AES", "BC");
		final SecretKeySpec keySpec = new SecretKeySpec(shortenKey(keyExchange.getKey(), 256), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		
		// Generate message
	    byte[] message = new BigInteger(messageLength, new Random()).toByteArray();
	    System.out.println("AESEncryption::Send(Socket,int) >> Generated message "+new HexString(message));

		// Encrypt data
	    System.out.println("AESEncryption::Send(Socket,int) >> Encrypting");
		byte[] encrypt = cipher.doFinal(message);
		
		// Send data
		System.out.println("AESEncryption::Send(Socket,int) >> Sending data '"+new HexString(encrypt)+"'");
		new Frame(encrypt).write(socket);

		System.out.println("AESEncryption::Send(Socket,int) >> Done.");
		
	}

	@Override
	public void receive(@NonNull Socket socket) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException, IOException, InvalidParameterSpecException {
		Security.addProvider(new BouncyCastleProvider());

		// Exchange Keys
		keyExchange.receive(socket);
		
		// Initialize AES Cipher
		Cipher cipher = Cipher.getInstance("AES", "BC");
		final SecretKeySpec keySpec = new SecretKeySpec(shortenKey(keyExchange.getKey(), 256), "AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);

		// Receive data
		System.out.println("AESEncryption::Receive(Socket) >> Receiving data");
		byte[] encrypt = Frame.get(socket).data;

		System.out.println("AESEncryption::Receive(Socket) >> Encrypting data");
		byte[] decryptByte = cipher.doFinal(encrypt);
		
		System.out.println("AESEncryption::Receive(Socket) >> Received: "+new HexString(decryptByte));
	}
	
	private byte[] shortenKey(final byte[] key, int length) {
		final byte[] shortKey = new byte[length/8];
		
		System.arraycopy(key, 0,  shortKey, 0, shortKey.length);
		
		return shortKey;
	}
}
