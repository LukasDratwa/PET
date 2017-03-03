package confcost.controller.send;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.encryption.AsymmetricEncryption;
import confcost.controller.encryption.Encryption;
import confcost.controller.encryption.SymmetricEncryption;
import confcost.controller.ke.KeyExchange;
import confcost.model.Connection;
import confcost.model.CryptoIteration;
import confcost.model.CryptoPass;
import confcost.model.Model;
import confcost.model.SendMode;
import confcost.model.SendModeInstance;
import confcost.model.Connection.Status;
import confcost.network.Frame;
import confcost.util.HexString;

/**
 * Responsible for sending data in accordance with a {@link SendModeInstance}.
 * 
 * @author Marc Eichler
 *
 */
public class SendController {	
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
	 * @param mode	The {@link SendMode} 
	 * @param iterations The number of iterations
	 * @param generateKeyEveryIteration	true iff a new key should be generated for every iteration
	 * @param hostname	The host
	 * @param port	The port
	 */
	public void send(SendMode mode, int iterations, final boolean generateKeyEveryIteration, final @NonNull String hostname, final int port) throws GeneralSecurityException, IOException {
 		// Create Encryption object
 		Encryption encryption;
		try {
			Constructor<? extends Encryption> c = mode.messageExchange.getConstructor(SendMode.class);
			encryption = c.newInstance(mode);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return;
		}

		System.out.println("SendController >> Setup:");
		System.out.println("SendController >> \tHost:" + hostname+":"+port);
		System.out.println("SendController >> \tKey exchange: " + mode.keyExchange);
		System.out.println("SendController >> \tEncryption: " + mode.messageExchange);
		System.out.println("SendController >> \tMessage length: " + mode.messageLength);
		System.out.println("SendController >> \tKey length: " + mode.keyLength);
		System.out.println("SendController >> \tIterations: " + iterations);
		System.out.println("SendController >> \tKey Exchange every iteration? " + generateKeyEveryIteration);
		System.out.println("SendController >> Sending "+mode+" to "+hostname+":"+port);
		
		// Create socket
		Socket socket = new Socket(hostname, port);
	    socket.setSoTimeout(10000);
		System.out.println("SendController >> Connected.");
		
		Connection connection = new Connection(Connection.Type.OUT, mode, iterations, socket.getInetAddress());
		model.getConnectionModel().addConnection(connection);
		
		// Perform setup information exchange
		new ObjectOutputStream(socket.getOutputStream()).writeObject(mode);
		new DataOutputStream(socket.getOutputStream()).writeInt(iterations); 
		new DataOutputStream(socket.getOutputStream()).writeBoolean(generateKeyEveryIteration);

		CryptoPass cp = new CryptoPass(mode.messageExchange,
			mode.keyExchange,
			mode.keyLength,
			mode.messageLength,
			iterations);
		
		final BlockingQueue<String> queueText = new LinkedBlockingQueue<String>();
		Thread thread = new Thread(){    		
    		@Override
    		public void run(){
//    			while (true) {
//                    try {
//                        String dataString = queueText.take();
//                        ProgressBarWindow.setListValue(dataString);
//                    } catch (InterruptedException e) {
//                        System.err.println("Error occurred:" + e);
//                    }
//                }
    		}
    	};
    	thread.start();
		
    	connection.setStatus(Status.TRANSMITTING);
		// Retrieve key
		if (!generateKeyEveryIteration) {
		    System.out.println("SendController::send >> Retrieving initial keys.");
			// Get public key
			if (encryption instanceof AsymmetricEncryption) {
				AsymmetricEncryption e = (AsymmetricEncryption)encryption;
				e.setPublicKey(Frame.get(socket).data); // Get public key
			} 
			// Perform key exchange
			else if (encryption instanceof SymmetricEncryption) {
				SymmetricEncryption se = (SymmetricEncryption) encryption;
				KeyExchange ke = se.getKeyExchange();

			    System.out.println("SendController::send >> Exchanging keys.");
			    ke.setKeyLength(mode.keyLength);
				ke.send(socket);
				
				// Generate key
				se.generateKey(mode.keyLength, ke.getKey());
			    System.out.println("SendController::send >> Key: "+new HexString(se.getKey().getEncoded()));
			}
		}
		
		for (int i = 0; i < iterations; i++) {
			System.out.println("SencController::send >> *** Iteration "+(i+1)+"/"+iterations);
			CryptoIteration ci = new CryptoIteration(mode.messageExchange,
				mode.keyExchange,
				mode.keyLength,
				mode.messageLength);
			
			// Measured time in ns
			long initTime = -1;
			long remoteInitTime = -1;
			long encryptionTime = -1;
			long decryptionTime = -1;
			
			
			// Retrieve key
			if (generateKeyEveryIteration) {
			    System.out.println("SendController::send >> Retrieving iteration keys.");
				// Get public key
				if (encryption instanceof AsymmetricEncryption) {
					AsymmetricEncryption e = (AsymmetricEncryption)encryption;
					e.setPublicKey(Frame.get(socket).data); // Get public key
				} 
				// Perform key exchange
				else if (encryption instanceof SymmetricEncryption) {
					SymmetricEncryption se = (SymmetricEncryption) encryption;
					KeyExchange ke = se.getKeyExchange();

				    System.out.println("SendController::send >> Exchanging keys.");
				    ke.setKeyLength(mode.keyLength);
					ke.send(socket);
					
					// Generate key
					initTime = System.nanoTime();
					se.generateKey(mode.keyLength, ke.getKey());
					initTime = System.nanoTime() - initTime;
				    System.out.println("SendController::send >> Key: "+new HexString(se.getKey().getEncoded()));
				}
			}
			
			// Generate and encrypt message
			byte[] message = generateMessage(mode.messageLength);
		    encryptionTime = System.nanoTime();
			message = encryption.encrypt(message);
			encryptionTime = System.nanoTime() - encryptionTime;
			
			// Send encrypted message
		    System.out.println("SendController::send >> Sending message "+new HexString(message));
			new Frame(message).write(socket);
			
			// set progress text
			queueText.offer("Iteration " + (i+1) + " of " + iterations + ": Encryption");

		    // Receive key generation time
		    remoteInitTime = new DataInputStream(socket.getInputStream()).readLong();
		    // Receive decryption time
		    decryptionTime = new DataInputStream(socket.getInputStream()).readLong();
		    
		    ci.setInitTime(initTime/1000);
		    ci.setRemoteInitTime(remoteInitTime/1000);
		    ci.setEncryptionTime(encryptionTime/1000);
		    ci.setDecryptionTime(decryptionTime/1000);
		    
		    cp.add(ci);
	    	connection.setProgress(i+1);
		}
		
		connection.setStatus(Status.DONE);
	    
		this.model.addCryptoPass(cp);
		
	    // Close socket
		socket.close();
		System.out.println("SendController >> Done.");
	}
	
	/**
	 * Generates a new message of specified length
	 * 
	 * @param messageLength	The length in bit
	 * @return
	 */
	public byte[] generateMessage(int messageLength) {
		int byteLength = (int) Math.ceil(messageLength/8);
	    byte[] message = new BigInteger(messageLength, new Random()).toByteArray();
	    
	    // Length is correct, return message
	    if (message.length == byteLength) return message;

	    // BigInteger may have created one byte to much or several too few. Fix it.
	    byte[] correctMessage = new byte[byteLength];
	    for (int i = 0; i < byteLength; ++i) {
	    	if (i < message.length)
	    		correctMessage[i] = message[i];
	    	else correctMessage[i] = 0;
	    }
	    return correctMessage;
	}
}
