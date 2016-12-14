package confcost.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import confcost.network.Frame;

/**
 * A {@link Thread} to handle an incoming connection.
 * 
 * @author Marc Eichler
 *
 */
public class DispatchThread extends Thread {

	private final Socket socket;
	
	/**
	 * Create the {@link DispatchThread}.
	 * 
	 * @param socket	The {@link Socket}
	 */
	public DispatchThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			// Receive key exchange and encryption method
			System.out.println("DispatchThread >> Receiving KE and ENC methods");
			String keyEx = Frame.get(socket).toString(); // Ignored for now
			String enc = Frame.get(socket).toString();
			System.out.println("DispatchThread >> "+keyEx+"|"+enc);
			
			// Receive key length
			System.out.println("DispatchThread >> Receiving key length");
			int keyLength = new DataInputStream(socket.getInputStream()).readInt();
			System.out.println("DispatchThread >> Key length is "+keyLength+" bit");
			
			// Generate key pair
			System.out.println("DispatchThread >> Generating key pair");
			KeyPairGenerator gen = KeyPairGenerator.getInstance(enc);
			gen.initialize(new RSAKeyGenParameterSpec(keyLength, new BigInteger("3")));
			KeyPair keys = gen.genKeyPair();
			
			// Send public key
			System.out.println("DispatchThread >> Sending public key '"+new String(keys.getPublic().getEncoded()));
			new Frame(keys.getPublic().getEncoded()).write(socket);
			
			// Receive data
			System.out.println("DispatchThread >> Receiving data");
			byte[] encrypt = Frame.get(socket).data;

			System.out.println("DispatchThread >> Encrypting data");
			Cipher cipher = Cipher.getInstance(enc);
			cipher.init(Cipher.DECRYPT_MODE, keys.getPrivate());
			byte[] decryptByte = cipher.doFinal(encrypt);
			
			System.out.println("DispatchThread >> Encrypted: "+new String(decryptByte));
			
		} catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		
		// Close the socket
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
