package confcost.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.Random;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.encryption.AESEncryption;
import confcost.controller.encryption.AsymmetricEncryption;
import confcost.controller.encryption.RSAEncryption;
import confcost.controller.encryption.SymmetricEncryption;
import confcost.controller.ke.KeyExchange;
import confcost.controller.ke.KeyExchangeFactory;
import confcost.model.CProtocol;
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
	private static final String DEFAULT_PROVIDER = "BC";

	/**
	 * Creates a new {@link SendController}.
	 * 
	 * @param host	The receiver host name or IP
	 * @param port	The receiver port
	 */
	public SendController() {
	}
	
	public void connect() throws UnknownHostException, IOException {
	}
	
	/**
	 * Sends one or more encrypted messages to the specified host, according to the specified parameters 
	 * @param instance	The parameters
	 * @param hostname	The host
	 * @param port	The port
	 */
	public void send(SendModeInstance instance, final @NonNull String hostname, final int port) throws GeneralSecurityException, IOException {
		System.out.println("SendController >> Sending "+instance+" to "+hostname+":"+port);
		Socket socket = new Socket(hostname, port);
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
		new Frame(instance.getSendMode().keyExchange.getName()).write(socket);
		new Frame(instance.getSendMode().messageExchange.getName()).write(socket);
		
		new DataOutputStream(socket.getOutputStream()).writeInt(instance.getKeyLength()); // Send message length
		
		// RSA
		if (instance.getSendMode().messageExchange == CProtocol.RSA) {
			// Run encryption
			AsymmetricEncryption e = new RSAEncryption(DEFAULT_PROVIDER);
			e.setPublicKey(Frame.get(socket).data); // Get public key
			
			// Generate and encrypt message
		    byte[] message = new BigInteger(instance.getMessageLength(), new Random()).toByteArray();
		    System.out.println("SendController::send >> Generated message "+new HexString(message));
			message = e.encrypt(message);
			
			// Send encrypted message
		    System.out.println("SendController::send >> Sending "+new HexString(message));
			new Frame(message).write(socket);
		    System.out.println("SendController::send >> Message sent.");
		} 
		// AES
		else if (instance.getSendMode().messageExchange == CProtocol.AES) {
			KeyExchange ke = KeyExchangeFactory.get(instance.getSendMode().keyExchange);

		    System.out.println("SendController::send >> Exchanging keys.");
		    ke.setKeyLength(512);
			ke.send(socket);
			
			SymmetricEncryption e = new AESEncryption(DEFAULT_PROVIDER, ke);
			
			// Generate key
			e.generateKey(instance.getKeyLength(), ke.getKey());
		    System.out.println("SendController::send >> AES Key: "+new HexString(e.getKey().getEncoded()));
		    
			// Generate and encrypt message
		    byte[] message = new BigInteger(instance.getMessageLength(), new Random()).toByteArray();
		    System.out.println("SendController::send >> Generated message "+new HexString(message));
			message = e.encrypt(message);
			
			// Send message
		    System.out.println("SendController::send >> Sending "+new HexString(message));
			new Frame(message).write(socket);
		    System.out.println("SendController::send >> Message sent.");
		}
		
	    // Close socket
		socket.close();
		System.out.println("SendController >> Done.");
	}
}
