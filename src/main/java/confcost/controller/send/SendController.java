package confcost.controller.send;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.util.Random;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.algorithm.Algorithm;
import confcost.controller.algorithm.AsymmetricEncryption;
import confcost.controller.algorithm.Encryption;
import confcost.controller.algorithm.Signature;
import confcost.controller.algorithm.SymmetricEncryption;
import confcost.controller.ke.KeyExchange;
import confcost.model.Connection;
import confcost.model.Connection.Status;
import confcost.model.CryptoIteration;
import confcost.model.CryptoPass;
import confcost.model.Model;
import confcost.model.SendMode;
import confcost.network.Frame;
import confcost.util.HexString;

/**
 * Responsible for sending data in accordance with a {@link SendModeInstance}.
 * 
 * @author Marc Eichler
 *
 */
public class SendController {
	/**
	 * The socket timeout in ms
	 */
	private static final int SOCKET_TIMEOUT = 1000*60*60; // 1h
	
	/**
	 * The main {@link Model}
	 */
	private final @NonNull Model model;

	/**
	 * Creates a new {@link SendController}.
	 * @param model	The main {@link Model}
	 */
	public SendController(final @NonNull Model model) {
		this.model = model;
	}
	
	/**
	 * Sends one or more encrypted messages to the specified host, according to the specified parameters
	 * @param mode	The {@link SendMode} 
	 * @param iterations The number of iterations
	 * @param generateKeyEveryIteration	true iff a new key should be generated for every iteration
	 * @param hostname	The host
	 * @param port	The port
	 * @throws IOException If an IO error occurred
	 * @throws ReflectiveOperationException	If a reflective operation failed 
	 * 			(usually because an {@link Algorithm} or {@link KeyExchange} could not be instanced)
	 */
	public void send(final @NonNull SendMode mode, 
			final @NonNull String hostname, final int port) throws ReflectiveOperationException, IOException {

		// Print information
		this.printDebug(mode, hostname, port);
		
 		// Create Signature/Encryption objects
		Signature signature;
		Encryption encryption;
		if (mode.signature != null) {
			signature = createSignature(mode);
			encryption = signature.getEncryption();
		} else {
			signature = null;
			encryption = createEncryption(mode);
		}

		// Create socket
		Socket socket = new Socket(hostname, port);
	    socket.setSoTimeout(SOCKET_TIMEOUT);
		System.out.println("SendController >> Connected.");

		// Create Connection object
		Connection connection;
		if (socket.getInetAddress().isLoopbackAddress())
			connection = new Connection(Connection.Type.LOCAL, mode, socket.getInetAddress());
		else
			connection = new Connection(Connection.Type.OUT, mode, socket.getInetAddress());
		model.getConnectionModel().addConnection(connection);
		
		try {
			performExchange(socket, signature, encryption, mode, connection);
		} catch (Exception e) {
			e.printStackTrace();
			connection.setError(e);
		}
		
		System.out.println("SendController >> Done.");
	}

	/**
	 * Creates a {@link Signature} based on the specified {@link SendMode}
	 * @param mode	The {@link SendMode}
	 * @return	The {@link Signature}
	 */
	private final @NonNull Signature createSignature(final @NonNull SendMode mode) throws ReflectiveOperationException {
		Constructor<? extends Signature> c = mode.signature.getConstructor(SendMode.class);
		Signature signature = c.newInstance(mode);
		return signature;
	}
	
	/**
	 * Creates an encryption based on the specified {@link SendMode}
	 * @param mode	The {@link SendMode}
	 * @return	The {@link Encryption}
	 */
	private final @NonNull Encryption createEncryption(final @NonNull SendMode mode) throws ReflectiveOperationException {
		Constructor<? extends Encryption> c = mode.encryption.getConstructor(SendMode.class);
		Encryption encryption = c.newInstance(mode);
		return encryption;
	}
	
	/**
	 * Prints a debug message, containing the initial setup
	 * @param hostname	The host name
	 * @param port	The port
	 * @param mode	The {@link SendMode}
	 */
	private void printDebug(final @NonNull SendMode mode, 
			final @NonNull String hostname, final int port) {
		System.out.println("SendController >> Setup:");
		System.out.println("SendController >> \tHost:" + hostname+":"+port);
		System.out.println("SendController >> \tKey exchange: " + mode.keyExchange);
		System.out.println("SendController >> \tEncryption: " + mode.encryption);
		System.out.println("SendController >> \tMessage length: " + mode.messageLength);
		System.out.println("SendController >> \tKey length: " + mode.keyLength);
		System.out.println("SendController >> \tIterations: " + mode.iterations);
		System.out.println("SendController >> \tKey Exchange every iteration? " + mode.generateKeyEveryIteration);
		System.out.println("SendController >> Sending "+mode+" to "+hostname+":"+port);
	}
	
	/**
	 * Performs the message exchanges with the remote end
	 * @throws IOException 
	 * @param socket
	 * @param encryption
	 * @param mode
	 * @param connection
	 * @throws IOException
	 * @throws GeneralSecurityException 
	 */
	private void performExchange(@NonNull Socket socket, 
			final @NonNull Signature signature, @NonNull Encryption encryption, 
			final @NonNull SendMode mode, final @NonNull Connection connection) throws IOException, GeneralSecurityException {
	
		// Perform setup information exchange
		new ObjectOutputStream(socket.getOutputStream()).writeObject(mode);

		CryptoPass cp = new CryptoPass(mode);
		
    	connection.setStatus(Status.TRANSMITTING);
    	
    	// Measured init time in ns
    	long initTime = -1;
		// Retrieve key
		if (!mode.generateKeyEveryIteration) {
			initTime = generateKey(socket, encryption, mode);
		}
		
		for (int i = 0; i < mode.iterations; i++) {
			System.out.println("SencController::send >> *** Iteration "+(i+1)+"/"+mode.iterations);
			CryptoIteration ci = cp.createIteration(i);
			
			// Measured time in ns
			long remoteInitTime = -1;
			long encryptionTime = -1;
			long decryptionTime = -1;
			
			
			// Retrieve key
			if (mode.generateKeyEveryIteration) {
				initTime = generateKey(socket, encryption, mode);
			}
			
			// Generate and encrypt message
			byte[] message = generateMessage(mode.messageLength);
		    System.out.println("SendController::send >> Generated message "+new HexString(message));
			
			if (signature != null) {
			    encryptionTime = System.nanoTime();
				final @NonNull byte[] sig = signature.sign(message);
				encryptionTime = System.nanoTime() - encryptionTime;
				// Send encrypted/signed message
			    System.out.println("SendController::send >> Sending message "+new HexString(message));
				new Frame(message).write(socket);
			    System.out.println("SendController::send >> Sending signature "+new HexString(sig));
				new Frame(sig).write(socket);
			} else {
			    encryptionTime = System.nanoTime();
				message = encryption.encrypt(message);
				encryptionTime = System.nanoTime() - encryptionTime;
				// Send encrypted/signed message
			    System.out.println("SendController::send >> Sending message "+new HexString(message));
				new Frame(message).write(socket);
			}

		    // Receive key generation time
		    remoteInitTime = new DataInputStream(socket.getInputStream()).readLong();
		    // Receive decryption time
		    decryptionTime = new DataInputStream(socket.getInputStream()).readLong();
		    
		    // Set elapsed time
		    ci.setInitTime(initTime/1000);
		    ci.setRemoteInitTime(remoteInitTime/1000);
		    ci.setEncryptionTime(encryptionTime/1000);
		    ci.setDecryptionTime(decryptionTime/1000);
		    
		    // Update connection progress
	    	connection.setProgress(i+1);
		}
		
		connection.setStatus(Status.DONE);
	    
		this.model.addCryptoPass(cp);
	}
	
	/**
	 * Performs the key generation and key exchange.
	 * 
	 * @param socket	The socket to the receiving end
	 * @param encryption	The {@link Algorithm}
	 * @param mode	The {@link SendMode}
	 * @return	The elapsed time for key generation
	 * @throws GeneralSecurityException
	 * @throws IOException 
	 */
	private long generateKey(@NonNull Socket socket, @NonNull Encryption encryption, 
			final @NonNull SendMode mode) throws GeneralSecurityException, IOException {
		long time = -1;
		
		if (mode.signature != null) {
		    System.out.println("SendController::send >> Generating iteration keys.");
			// Get public key
			if (encryption instanceof AsymmetricEncryption) {
				AsymmetricEncryption ae = (AsymmetricEncryption)encryption;
				time = System.nanoTime();
				ae.generateKeyPair(mode.keyLength);
			    time = System.nanoTime() - time;
				System.out.println("SendController::send >> Sending public key");
				new Frame(ae.getPublicKey().getEncoded()).write(socket);
			} 
			// Perform key exchange
			else if (encryption instanceof SymmetricEncryption) {
				SymmetricEncryption se = (SymmetricEncryption) encryption;
				KeyExchange ke = se.getKeyExchange();
	
			    System.out.println("SendController::send >> Exchanging keys.");
				ke.receive(socket);
				
				// Generate key
				time = System.nanoTime();
				se.generateKey(mode.keyLength, ke.getKey());
			    time = System.nanoTime() - time;
			    System.out.println("SendController::send >> Key: "+new HexString(se.getSecretKey().getEncoded()));
			}
		} else {
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
				time = System.nanoTime();
				se.generateKey(mode.keyLength, ke.getKey());
				time = System.nanoTime() - time;
			    System.out.println("SendController::send >> Key: "+new HexString(se.getSecretKey().getEncoded()));
			}
		}
			
		return time;
	}
	
	/**
	 * Generates a random message of specified length.
	 * @param messageLength	The length in bit
	 * @return	the generated message
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
