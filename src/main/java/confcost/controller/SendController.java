package confcost.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.encryption.AsymmetricEncryption;
import confcost.controller.encryption.RSAEncryption;
import confcost.model.CProtocol;
import confcost.model.KEProtocol;
import confcost.model.SendModeInstance;
import confcost.network.Frame;
import confcost.util.HexString;

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
	
	public void send(SendModeInstance instance) throws UnknownHostException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidParameterSpecException {
		System.out.println("SendController >> Sending "+instance+" to "+host+":"+port);
		Socket socket = new Socket(host, port);
	    socket.setSoTimeout(10000);
		System.out.println("SendController >> Connected.");
		
//		new Frame(instance.getSendMode().keyExchange.getName()).write(socket);
//		new Frame(instance.getSendMode().messageExchange.getName()).write(socket);
		
		// Get KeyExchange
//		KeyExchange ke = KeyExchangeFactory.get(instance.getSendMode().keyExchange);
//		ke.setKeyLength(instance.getKeyLength());
		
		// Perform key exchange and send message
//		AESEncryption e = new AESEncryption(ke);
//		e.send(socket, instance.getMessageLength());

		// Perform setup information exchange
		new Frame(KEProtocol.None.getName()).write(socket);
		new Frame(CProtocol.RSA.getName()).write(socket);
		
		new DataOutputStream(socket.getOutputStream()).writeInt(instance.getKeyLength()); // Send message length
		
		// Run encryption
		AsymmetricEncryption e = new RSAEncryption("BC");
		e.setPublicKey(Frame.get(socket).data); // Get public key
		
		// Generate and encrypt message
	    byte[] message = new BigInteger(instance.getMessageLength(), new Random()).toByteArray();
	    System.out.println("SendController::send >> Generated message "+new HexString(message));
		message = e.encrypt(message);
		
		// Send encrypted message
	    System.out.println("SendController::send >> Sending "+new HexString(message));
		new Frame(message).write(socket);
	    System.out.println("SendController::send >> Message sent.");
		
	    // Close socket
		socket.close();
		System.out.println("SendController >> Done.");
	}
}
