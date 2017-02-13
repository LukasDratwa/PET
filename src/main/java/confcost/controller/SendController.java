package confcost.controller;

import java.io.DataInputStream;
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
import confcost.controller.encryption.ECIESEncryption;
import confcost.controller.encryption.RC2Encryption;
import confcost.controller.encryption.RSAEncryption;
import confcost.controller.encryption.SymmetricEncryption;
import confcost.controller.ke.KeyExchange;
import confcost.controller.ke.KeyExchangeFactory;
import confcost.model.CProtocol;
import confcost.model.CryptoIteration;
import confcost.model.CryptoPass;
import confcost.model.Model;
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
	
	private final @NonNull Model model;

	/**
	 * Creates a new {@link SendController}.
	 * 
	 * @param host	The receiver host name or IP
	 * @param port	The receiver port
	 */
	public SendController(final @NonNull Model model) {
		this.model = model;
	}
	
	public void connect() throws UnknownHostException, IOException {
	}
	
	/**
	 * Sends one or more encrypted messages to the specified host, according to the specified parameters 
	 * @param instance	The parameters
	 * @param hostname	The host
	 * @param port	The port
	 */
	public void send(SendModeInstance instance, int iterations, final @NonNull String hostname, final int port) throws GeneralSecurityException, IOException {
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
		new DataOutputStream(socket.getOutputStream()).writeInt(iterations); // Send iteration count
		new DataOutputStream(socket.getOutputStream()).writeInt(instance.getKeyLength()); // Send message length
		
		CryptoPass cp = new CryptoPass(instance.getSendMode().messageExchange,
																							instance.getSendMode().keyExchange,
																							instance.getKeyLength(),
																							instance.getMessageLength(),
																							iterations);
		
		for (int i = 0; i < iterations; i++) {
			CryptoIteration ci = new CryptoIteration(instance.getSendMode().messageExchange,
																								instance.getSendMode().keyExchange,
																								instance.getKeyLength(),
																								instance.getMessageLength());
			
			// Measured time in ns
			long initTime = -1;
			long remoteInitTime = -1;
			long encryptionTime = -1;
			long decryptionTime = -1;
			
			// RSA
			if (instance.getSendMode().messageExchange == CProtocol.RSA) {
				// Run encryption
				AsymmetricEncryption e = new RSAEncryption(DEFAULT_PROVIDER);
				e.setPublicKey(Frame.get(socket).data); // Get public key
				
				// Generate and encrypt message
			    byte[] message = new BigInteger(instance.getMessageLength(), new Random()).toByteArray();
			    System.out.println("SendController::send >> Generated message "+new HexString(message));
			    encryptionTime = System.nanoTime();
				message = e.encrypt(message);
				encryptionTime = System.nanoTime() - encryptionTime;
				
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
				initTime = System.nanoTime();
				e.generateKey(instance.getKeyLength(), ke.getKey());
				initTime = System.nanoTime() - initTime;
			    System.out.println("SendController::send >> AES Key: "+new HexString(e.getKey().getEncoded()));
			    
				// Generate and encrypt message
			    byte[] message = new BigInteger(instance.getMessageLength(), new Random()).toByteArray();
			    System.out.println("SendController::send >> Generated message "+new HexString(message));
			    encryptionTime = System.nanoTime();
				message = e.encrypt(message);
				encryptionTime = System.nanoTime() - encryptionTime;
				
				// Send message
			    System.out.println("SendController::send >> Sending "+new HexString(message));
				new Frame(message).write(socket);
			    System.out.println("SendController::send >> Message sent.");
			}
			// RC2
			else if (instance.getSendMode().messageExchange == CProtocol.RC2) {
				KeyExchange ke = KeyExchangeFactory.get(instance.getSendMode().keyExchange);

			    System.out.println("SendController::send >> Exchanging keys.");
			    ke.setKeyLength(512);
				ke.send(socket);
				
				SymmetricEncryption e = new RC2Encryption(DEFAULT_PROVIDER, ke);
				
				// Generate key
				initTime = System.nanoTime();
				e.generateKey(instance.getKeyLength(), ke.getKey());
				initTime = System.nanoTime() - initTime;
				System.out.println("SendController::send >> RC2 Key: "+e.getKey().getEncoded().toString());
			    System.out.println("SendController::send >> RC2 Key: "+new HexString(e.getKey().getEncoded()));
			    
				// Generate and encrypt message
			    byte[] message = new BigInteger(instance.getMessageLength(), new Random()).toByteArray();
			    System.out.println("SendController::send >> Generated message "+new HexString(message));
			    encryptionTime = System.nanoTime();
				message = e.encrypt(message);
				encryptionTime = System.nanoTime() - encryptionTime;
				
				// Send message
			    System.out.println("SendController::send >> Sending "+new HexString(message));
				new Frame(message).write(socket);
			    System.out.println("SendController::send >> Message sent.");
			}
			 // ECIES
			else if (instance.getSendMode().messageExchange == CProtocol.ECIES) {
				// Run encryption
				AsymmetricEncryption e = new ECIESEncryption(DEFAULT_PROVIDER);
				e.setPublicKey(Frame.get(socket).data); // Get public key
				
				// Generate and encrypt message
			    byte[] message = new BigInteger(instance.getMessageLength(), new Random()).toByteArray();
			    System.out.println("SendController::send >> Generated message "+new HexString(message));
			    encryptionTime = System.nanoTime();
				message = e.encrypt(message);
				encryptionTime = System.nanoTime() - encryptionTime;
				
				// Send encrypted message
			    System.out.println("SendController::send >> Sending "+new HexString(message));
				new Frame(message).write(socket);
			    System.out.println("SendController::send >> Message sent.");
			}else throw new IllegalStateException("Unsupported encryption method: "+instance.getSendMode().messageExchange);

		    // Receive key generation time
		    remoteInitTime = new DataInputStream(socket.getInputStream()).readLong();
		    // Receive decryption time
		    decryptionTime = new DataInputStream(socket.getInputStream()).readLong();
		    
		    ci.setInitTime(initTime/1000000);
		    ci.setRemoteInitTime(remoteInitTime/1000000);
		    ci.setEncryptionTime(encryptionTime/1000000);
		    ci.setDecryptionTime(decryptionTime/1000000);
		    
		    cp.add(ci);
		}
	    
		this.model.addCryptoPass(cp);
		
	    // Close socket
		socket.close();
		System.out.println("SendController >> Done.");
	}
}
