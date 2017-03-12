package confcost.controller.receive;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.security.GeneralSecurityException;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.algorithm.Algorithm;
import confcost.controller.algorithm.AsymmetricEncryption;
import confcost.controller.algorithm.Encryption;
import confcost.controller.algorithm.Signature;
import confcost.controller.algorithm.SymmetricEncryption;
import confcost.controller.ke.KeyExchange;
import confcost.model.Connection;
import confcost.model.Connection.Status;
import confcost.model.Model;
import confcost.model.SendMode;
import confcost.network.Frame;
import confcost.util.HexString;

/**
 * A {@link Thread} to handle an incoming connection.
 * 
 * @author Marc Eichler
 *
 */
public class HandlerThread extends Thread {
	/**
	 * The {@link Socket} to the host
	 */
	private final @NonNull Socket socket;
	
	/**
	 * The main {@link Model}
	 */
	private final @NonNull Model model;
	
	/**
	 * Create the {@link HandlerThread}.
	 * 
	 * @param socket	The {@link Socket}
	 * @param model		The main {@link Model}
	 */
	public HandlerThread(@NonNull Socket socket, @NonNull Model model) {
		this.socket = socket;
		this.model = model;
	}
	
	@Override
	public void run() {
		try {
			// Receive key exchange and encryption method
			System.out.println("HandlerThread >> Receiving setup data");
			SendMode mode = (SendMode)new ObjectInputStream(socket.getInputStream()).readObject();
			
			System.out.println("HandlerThread >> Setup:");
			System.out.println("HandlerThread >> \tKey exchange: " + mode.keyExchange);
			System.out.println("HandlerThread >> \tEncryption: " + mode.encryption);
			System.out.println("HandlerThread >> \tMessage length: " + mode.messageLength);
			System.out.println("HandlerThread >> \tKey length: " + mode.keyLength);
			System.out.println("HandlerThread >> \tIterations: " + mode.iterations);
			System.out.println("HandlerThread >> \tGenerate key every iteration? " + mode.generateKeyEveryIteration);
	 		
			// Create Signature/Encryption object
			Signature signature;
	 		Encryption encryption;
	 		if (mode.signature != null) {
	 			signature = createSignature(mode);
	 			encryption = signature.getEncryption();
	 		} else {
	 			signature = null;
	 			encryption = createEncryption(mode);
	 		}
	    	
			System.out.println("HandlerThread >> Key length: "+mode.keyLength);

			// If connection is not local, create a new Connection. Otherwise, the sender will handle the Connection
			Connection connection = null;
			if (!socket.getInetAddress().isLoopbackAddress()) {
				connection = new Connection(Connection.Type.IN, mode, socket.getInetAddress());
				this.model.getConnectionModel().addConnection(connection);
			}
			
			long initTime = -1;
			
			// Initial key generation key
			if (!mode.generateKeyEveryIteration) {
				initTime = generateKey(socket, encryption, mode);
			}
			
			try{
				if (connection != null) connection.setStatus(Status.TRANSMITTING);
				
				for (int i = 0; i < mode.iterations; i++) {
					System.out.println("HandlerThread >> *** Iteration "+(i+1)+"/"+mode.iterations);
					long runTime = -1;
					
					// Key generation
					if (mode.generateKeyEveryIteration) {
						initTime = generateKey(socket, encryption, mode);
					}
					
					// Retrieve and verify/decrypt message
					byte[] message = Frame.get(socket).data;
					System.out.println("HandlerThread >> Received: "+new HexString(message));

					if (signature != null) {
						byte[] sig = Frame.get(socket).data;
						System.out.println("HandlerThread >> Signature: "+new HexString(sig));
					    runTime = System.nanoTime();
						System.out.println("Verified: "+signature.verify(message, sig));
						runTime = System.nanoTime() - runTime;
					} else {
					    runTime = System.nanoTime();
						message = encryption.decrypt(message);
						runTime = System.nanoTime() - runTime;
						System.out.println("HandlerThread >> Encrypted: "+new HexString(message));
					}
					
					System.out.println("HandlerThread >> Done.");
					
				    // Send back measured times
					System.out.println("HandlerThread >> Measured "+initTime +", "+runTime);
					
					if (mode.generateKeyEveryIteration)
						new DataOutputStream(socket.getOutputStream()).writeLong(initTime);
				    new DataOutputStream(socket.getOutputStream()).writeLong(runTime);
				    
				    if (connection != null) {
				    	connection.setProgress(i+1);
			    	}
				}
				
				if (!mode.generateKeyEveryIteration)
					new DataOutputStream(socket.getOutputStream()).writeLong(initTime);
			} catch (IOException e1) {
				e1.printStackTrace();
				if (connection != null) {
					connection.setStatus(Status.ERROR);
					connection.setError(e1);
				}
			}

			// Close the socket
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				if (connection != null && connection.getStatus() != Status.ERROR) {
					connection.setStatus(Status.ERROR);
					connection.setError(e);
				}
			}
			if (connection != null) connection.setStatus(Status.DONE);
			
		}catch(Exception e){
			e.printStackTrace();
		}
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
		
		if (mode.signature == null) {
		    System.out.println("HandlerThread >> Generating iteration keys.");
			// Get public key
			if (encryption instanceof AsymmetricEncryption) {
				AsymmetricEncryption ae = (AsymmetricEncryption)encryption;
				time = System.nanoTime();
				ae.generateKeyPair(mode.keyLength);
			    time = System.nanoTime() - time;
				System.out.println("HandlerThread >> Sending public key");
				new Frame(ae.getPublicKey().getEncoded()).write(socket);
			} 
			// Perform key exchange
			else if (encryption instanceof SymmetricEncryption) {
				SymmetricEncryption se = (SymmetricEncryption) encryption;
				KeyExchange ke = se.getKeyExchange();
	
			    System.out.println("HandlerThread >> Exchanging keys.");
				ke.receive(socket);
				
				// Generate key
				time = System.nanoTime();
				se.generateKey(mode.keyLength, ke.getKey());
			    time = System.nanoTime() - time;
			    System.out.println("HandlerThread >> Key: "+new HexString(se.getSecretKey().getEncoded()));
			}
		} else {
		    System.out.println("HandlerThread >> Retrieving initial keys.");
		    
			// Get public key
			if (encryption instanceof AsymmetricEncryption) {
				AsymmetricEncryption e = (AsymmetricEncryption)encryption;
				
				e.setPublicKey(Frame.get(socket).data); // Get public key
			} 
			// Perform key exchange
			else if (encryption instanceof SymmetricEncryption) {
				SymmetricEncryption se = (SymmetricEncryption) encryption;
				KeyExchange ke = se.getKeyExchange();

			    System.out.println("HandlerThread >> Exchanging keys.");
			    ke.setKeyLength(mode.keyLength);
				ke.send(socket);

				// Generate key
				time = System.nanoTime();
				se.generateKey(mode.keyLength, ke.getKey());
				time = System.nanoTime() - time;
			    System.out.println("HandlerThread >> Key: "+new HexString(se.getSecretKey().getEncoded()));
			}
			
			return time;
		}
		
		return time;
	}
}
