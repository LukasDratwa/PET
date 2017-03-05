package confcost.controller.receive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import confcost.controller.encryption.AsymmetricEncryption;
import confcost.controller.encryption.Encryption;
import confcost.controller.encryption.SymmetricEncryption;
import confcost.controller.ke.KeyExchange;
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
	private final Socket socket;
	
	/**
	 * Create the {@link HandlerThread}.
	 * 
	 * @param socket	The {@link Socket}
	 */
	public HandlerThread(Socket socket) {
		this.socket = socket;
		//final BlockingQueue<Integer>  = new LinkedBlockingQueue<Integer>();
		
		
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

//			final BlockingQueue<Integer> queueProgressBar = new LinkedBlockingQueue<Integer>();
//			final BlockingQueue<String> queueText = new LinkedBlockingQueue<String>();
//			Thread thread = new Thread(){
//	    		ProgressBarWindow pbw;
//	    		
//	    		@Override
//	    		public void run(){
//	    			pbw = new ProgressBarWindow(/*enc.getName()*/"ASDF", iterations);
//	    			while (true) {
//	                    try {
//	                        int dataInt = queueProgressBar.take();
//	                        pbw.setProgressBarValue(dataInt);
//	                    } catch (InterruptedException e) {
//	                        System.err.println("Error occurred:" + e);
//	                    }
//	                    
//	                    try {
//	                        String dataString = queueText.take();
//	                        ProgressBarWindow.setListValue(dataString);
//	                    } catch (InterruptedException e) {
//	                        System.err.println("Error occurred:" + e);
//	                    }
//	                }
//	    		}
//	    	};
//	    	thread.start();
			
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

					se.generateKey(mode.keyLength, ke.getKey());
				    System.out.println("HandlerThread >> Generated Key: "+new HexString(se.getKey().getEncoded()));
				    
				}
			}
			
			
			try{
				for (int i = 0; i < iterations; i++) {
					System.out.println("HandlerThread >> *** Iteration "+(i+1)+"/"+iterations);
					long initTime = -1;
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
				    
				    // update progress bar
//				    queueProgressBar.offer(i+1);
//				    // set progress text
//					queueText.offer("Iteration " + (i+1) + " of " + iterations + ": Decryption");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// Close the socket
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
