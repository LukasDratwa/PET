package confcost.controller.receive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.encryption.AsymmetricEncryption;
import confcost.controller.encryption.Encryption;
import confcost.controller.encryption.SymmetricEncryption;
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
			final int iterations = new DataInputStream(socket.getInputStream()).readInt();
			final boolean generateKeyEveryIteration = new DataInputStream(socket.getInputStream()).readBoolean();
			
			System.out.println("HandlerThread >> Setup:");
			System.out.println("HandlerThread >> \tKey exchange: " + mode.keyExchange);
			System.out.println("HandlerThread >> \tEncryption: " + mode.messageExchange);
			System.out.println("HandlerThread >> \tMessage length: " + mode.messageLength);
			System.out.println("HandlerThread >> \tKey length: " + mode.keyLength);
			System.out.println("HandlerThread >> \tIterations: " + iterations);
			System.out.println("HandlerThread >> \tGenerate key every iteration? " + generateKeyEveryIteration);
	 		
			// Create Encryption object
	 		Encryption encryption;
			try {
				Constructor<? extends Encryption> c = mode.messageExchange.getConstructor(SendMode.class);
				encryption = c.newInstance(mode);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
				socket.close();
				return;
			}
	    	
			System.out.println("HandlerThread >> Key length: "+mode.keyLength);

			// If connection is not local, create a new Connection. Otherwise, the SendController will handle the Connection
			Connection connection = null;
			if (!socket.getInetAddress().isLoopbackAddress()) {
				connection = new Connection(Connection.Type.IN, mode, iterations, socket.getInetAddress());
				this.model.getConnectionModel().addConnection(connection);
			}
			
			long initTime = -1;
			
			// Initial key generation key
			if (!generateKeyEveryIteration) {
			    System.out.println("HandlerThread >> Generating initial keys.");
				// Get public key
				if (encryption instanceof AsymmetricEncryption) {
					AsymmetricEncryption ae = (AsymmetricEncryption)encryption;
					ae.generateKeyPair(mode.keyLength);
					System.out.println("HandlerThread >> Sending public key");
					new Frame(ae.getPublicKey().getEncoded()).write(socket);
				} 
				// Perform key exchange
				else if (encryption instanceof SymmetricEncryption) {
					SymmetricEncryption se = (SymmetricEncryption) encryption;
					KeyExchange ke = se.getKeyExchange();

				    System.out.println("HandlerThread >> Exchanging keys.");
					ke.receive(socket);

					initTime = System.nanoTime();
					se.generateKey(mode.keyLength, ke.getKey());
				    initTime = System.nanoTime() - initTime;
				    System.out.println("HandlerThread >> Generated Key: "+new HexString(se.getKey().getEncoded()));
				    
				}
			}
			
			try{
				if (connection != null) {
					connection.setStatus(Status.TRANSMITTING);
				}
				for (int i = 0; i < iterations; i++) {
					System.out.println("HandlerThread >> *** Iteration "+(i+1)+"/"+iterations);
					long decryptTime = -1;
					
					// Key generation key
					if (generateKeyEveryIteration) {
					    System.out.println("HandlerThread >> Generating iteration keys.");
						// Get public key
						if (encryption instanceof AsymmetricEncryption) {
							AsymmetricEncryption ae = (AsymmetricEncryption)encryption;
							ae.generateKeyPair(mode.keyLength);
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
							initTime = System.nanoTime();
							se.generateKey(mode.keyLength, ke.getKey());
						    initTime = System.nanoTime() - initTime;
						    System.out.println("HandlerThread >> Key: "+new HexString(se.getKey().getEncoded()));
						}
					}
					
					// Retrieve and decrypt message
					byte[] message = Frame.get(socket).data;
					System.out.println("HandlerThread >> Received: "+new HexString(message));

				    decryptTime = System.nanoTime();
					message = encryption.decrypt(message);
					decryptTime = System.nanoTime() - decryptTime;
					System.out.println("HandlerThread >> Encrypted: "+new HexString(message));
					
					System.out.println("HandlerThread >> Done.");
					
				    // Send back measured times
					System.out.println("HandlerThread >> Measured "+initTime +", "+decryptTime);
				    new DataOutputStream(socket.getOutputStream()).writeLong(initTime);
				    new DataOutputStream(socket.getOutputStream()).writeLong(decryptTime);
				    
				    if (connection != null) {
				    	connection.setProgress(i+1);
			    	}
				}
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
	
}
