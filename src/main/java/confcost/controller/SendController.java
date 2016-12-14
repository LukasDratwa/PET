package confcost.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendModeInstance;
import confcost.network.Frame;

/**
 * Responsible for sending data in accordance with a {@link SendModeInstance}.
 * 
 * @author Marc Eichler
 *
 */
public class SendController {

	private final @NonNull String host;
	private final @NonNull int port;
	
	/**
	 * Creates a new {@link SendController}.
	 * 
	 * @param host	The receiver host name or IP
	 * @param port	The receiver port
	 */
	public SendController(@NonNull String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void connect() throws UnknownHostException, IOException {
	}
	
	public void send(SendModeInstance instance) throws UnknownHostException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		System.out.println("SendController >> Sending "+instance+" to "+host+":"+port);
		Socket socket = new Socket(host, port);
	    socket.setSoTimeout(1000);
		System.out.println("SendController >> Connected.");
		
		// Write key exchange and encyption method
		new Frame(instance.getSendMode().keyExchange.getName()).write(socket);
		new Frame(instance.getSendMode().messageExchange.getName()).write(socket);
		
		// Send key length
		new DataOutputStream(socket.getOutputStream()).writeInt(instance.getKeyLength());
		
		// Receive public key from receiver
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Frame.get(socket).data);
		PublicKey pubKey = KeyFactory.getInstance(instance.getSendMode().messageExchange.getName()).generatePublic(pubKeySpec);
		System.out.println("SendController >> Received PubKey");
		
		// Generate data
	    byte[] message = new BigInteger(instance.getMessageLength(), new Random()).toByteArray();
	    System.out.println("SendController >> Generated message "+new String(message));
	    
		// Encrypt data
	    System.out.println("SendController >> Encrypting");
		Cipher cipher = Cipher.getInstance(instance.getSendMode().messageExchange.getName());
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] encrypt = cipher.doFinal(message);
		
		// Send data
		System.out.println("SendController >> Sending data '"+new String(encrypt)+"'");
		new Frame(encrypt).write(socket);
		
		// Close socket
		socket.close();
		System.out.println("SendController >> Done.");
	}
}
