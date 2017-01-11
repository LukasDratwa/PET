package confcost.controller.encryption;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.jdt.annotation.NonNull;

import confcost.network.Frame;

/**
 * Bouncy Castle RSA Encryption
 * 
 * @author Marc Eichler
 *
 */
public class RSAEncryption extends AsymmetricEncryption {

	@Override
	public void send(@NonNull Socket socket, int messageLength)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
			InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidParameterSpecException, IOException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Security.addProvider(new BouncyCastleProvider());
		
		// Send key length
		new DataOutputStream(socket.getOutputStream()).writeInt(1024);
		
		// Receive public key from receiver
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Frame.get(socket).data);
		PublicKey pubKey = KeyFactory.getInstance("RSA", "BC").generatePublic(pubKeySpec);
		System.out.println("RSAEncryption::send >> Received PubKey");
		
		// Generate data
	    byte[] message = new BigInteger(messageLength, new Random()).toByteArray();
	    System.out.println("RSAEncryption::send >> Generated message "+new String(message));
	    
		// Encrypt data
	    System.out.println("RSAEncryption::send >> Encrypting");
		Cipher cipher = Cipher.getInstance("RSA", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] encrypt = cipher.doFinal(message);
		
		// Send data
		System.out.println("RSAEncryption::send >> Sending data '"+new String(encrypt)+"'");
		new Frame(encrypt).write(socket);
		
		// Close socket
		socket.close();
		System.out.println("RSAEncryption::send >> Done.");
	}

	@Override
	public void receive(@NonNull Socket socket) throws IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidParameterSpecException, IOException {
		Security.addProvider(new BouncyCastleProvider());
		
		// Receive key length
		System.out.println("RSAEncryption::receive >> Receiving key length");
		final int keyLength = new DataInputStream(socket.getInputStream()).readInt();
		System.out.println("RSAEncryption::receive >> Key length is "+keyLength+" bit");
		
		// Generate key pair
		System.out.println("RSAEncryption::receive >> Generating key pair");
		final KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "BC");
		gen.initialize(new RSAKeyGenParameterSpec(keyLength, new BigInteger("3")));
		final KeyPair keys = gen.genKeyPair();
		
		// Send public key
		System.out.println("RSAEncryption::receive >> Sending public key '"+new String(keys.getPublic().getEncoded()));
		new Frame(keys.getPublic().getEncoded()).write(socket);
		
		// Receive data
		System.out.println("RSAEncryption::receive >> Receiving data");
		byte[] encrypt = Frame.get(socket).data;

		System.out.println("RSAEncryption::receive >> Encrypting data");
		Cipher cipher = Cipher.getInstance("RSA", "BC");
		cipher.init(Cipher.DECRYPT_MODE, keys.getPrivate());
		byte[] decryptByte = cipher.doFinal(encrypt);
		
		System.out.println("RSAEncryption::receive >> Encrypted: "+new String(decryptByte));
		
	}

}
