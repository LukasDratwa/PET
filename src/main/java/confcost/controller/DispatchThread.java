package confcost.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.SwingUtilities;

import confcost.controller.encryption.AESEncryption;
import confcost.controller.encryption.AsymmetricEncryption;
import confcost.controller.encryption.ECIESEncryption;
import confcost.controller.encryption.RC2Encryption;
import confcost.controller.encryption.RSAEncryption;
import confcost.controller.encryption.SymmetricEncryption;
import confcost.controller.ke.KeyExchange;
import confcost.controller.ke.KeyExchangeFactory;
import confcost.model.CProtocol;
import confcost.model.KEProtocol;
import confcost.network.Frame;
import confcost.util.HexString;
import confcost.view.ProgressBarWindow;
import confcost.view.TabSend;

/**
 * A {@link Thread} to handle an incoming connection.
 * 
 * @author Marc Eichler
 *
 */
public class DispatchThread extends Thread {
	private static final String DEFAULT_PROVIDER = "BC";

	private final Socket socket;
	
	/**
	 * Create the {@link DispatchThread}.
	 * 
	 * @param socket	The {@link Socket}
	 */
	public DispatchThread(Socket socket) {
		this.socket = socket;
		//final BlockingQueue<Integer>  = new LinkedBlockingQueue<Integer>();
		
		
	}
	
	@Override
	public void run() {
		try {
			// Receive key exchange and encryption method
			System.out.println("DispatchThread >> Receiving KE and ENC methods");
			final KEProtocol keyEx = KEProtocol.get(Frame.get(socket).toString()); // Ignored for now
			final CProtocol enc = CProtocol.get(Frame.get(socket).toString());
			final int iterations = new DataInputStream(socket.getInputStream()).readInt();
			System.out.println("DispatchThread >> "+keyEx+"|"+enc+" *"+iterations);

			final int keyLength = new DataInputStream(socket.getInputStream()).readInt();
			System.out.println("DispatchThread >> Key length: "+keyLength);
								
			
			final BlockingQueue<Integer> queueProgressBar = new LinkedBlockingQueue<Integer>();
			final BlockingQueue<String> queueText = new LinkedBlockingQueue<String>();
			Thread thread = new Thread(){
	    		ProgressBarWindow pbw;
	    		
	    		@Override
	    		public void run(){
	    			pbw = new ProgressBarWindow(enc.getName(), iterations);
	    			while (true) {
	                    try {
	                        int dataInt = queueProgressBar.take();
	                        pbw.setProgressBarValue(dataInt);
	                    } catch (InterruptedException e) {
	                        System.err.println("Error occurred:" + e);
	                    }
	                    
	                    try {
	                        String dataString = queueText.take();
	                        ProgressBarWindow.setListValue(dataString);
	                    } catch (InterruptedException e) {
	                        System.err.println("Error occurred:" + e);
	                    }
	                }
	    		}
	    	};
	    	thread.start();
	    	
			try{
				for (int i = 0; i < iterations; i++) {
					System.out.println("DispatchThread >> Iteration: "+i+"/"+iterations);
					long initTime = -1;
					long decryptTime = -1;
										
					if (enc == CProtocol.RSA) {
						AsymmetricEncryption e = new RSAEncryption(DEFAULT_PROVIDER);
						
						// Generate and send public key
						initTime = System.nanoTime();
						e.generateKeyPair(keyLength);
					    initTime = System.nanoTime() - initTime;
						System.out.println("DispatchThread >> Sending public key: "+keyLength);
						new Frame(e.getPublicKey().getEncoded()).write(socket);;
						
						// Retrieve and decrypt message
						byte[] message = Frame.get(socket).data;
						System.out.println("DispatchThread >> Received: "+new HexString(message));

					    decryptTime = System.nanoTime();
						message = e.decrypt(message);
						decryptTime = System.nanoTime() - decryptTime;
						System.out.println("DispatchThread >> Encrypted: "+new HexString(message));
						
						System.out.println("DispatchThread >> Done.");
					}
					// AES
					else if (enc == CProtocol.AES) {
						KeyExchange ke = KeyExchangeFactory.get(keyEx);

					    System.out.println("DispatchThread >> Exchanging keys.");
						ke.receive(socket);
						
						SymmetricEncryption e = new AESEncryption(DEFAULT_PROVIDER, ke);
						
						// Generate key
						initTime = System.nanoTime();
						e.generateKey(keyLength, ke.getKey());
					    initTime = System.nanoTime() - initTime;
					    System.out.println("DispatchThread >> AES Key: "+new HexString(e.getKey().getEncoded()));
					    
						// Receive and decrypt message
					    byte[] message = Frame.get(socket).data;
					    System.out.println("DispatchThread >>  Received "+new HexString(message));
					    decryptTime = System.nanoTime();
						message = e.decrypt(message);
						decryptTime = System.nanoTime() - decryptTime;
					    System.out.println("DispatchThread >>  Message "+new HexString(message));
						
					    System.out.println("DispatchThread >> Iteration done.");
					}
					// ECIES
					else if (enc == CProtocol.ECIES) {
						AsymmetricEncryption e = new ECIESEncryption(DEFAULT_PROVIDER);
						
						// Generate and send public key
						initTime = System.nanoTime();
						e.generateKeyPair(keyLength);
					    initTime = System.nanoTime() - initTime;
						System.out.println("DispatchThread >> Sending public key: "+keyLength);
						new Frame(e.getPublicKey().getEncoded()).write(socket);;
						
						// Retrieve and decrypt message
						byte[] message = Frame.get(socket).data;
						System.out.println("DispatchThread >> Received: "+new HexString(message));

					    decryptTime = System.nanoTime();
						message = e.decrypt(message);
						decryptTime = System.nanoTime() - decryptTime;
						System.out.println("DispatchThread >> Encrypted: "+new HexString(message));
						
						System.out.println("DispatchThread >> Done.");
					} 
					// RC2
					else if (enc == CProtocol.RC2) {
						KeyExchange ke = KeyExchangeFactory.get(keyEx);

					    System.out.println("DispatchThread >> Exchanging keys.");
						ke.receive(socket);
						
						SymmetricEncryption e = new RC2Encryption(DEFAULT_PROVIDER, ke);
						
						// Generate key
						initTime = System.nanoTime();
						e.generateKey(keyLength, ke.getKey());
					    initTime = System.nanoTime() - initTime;
					    System.out.println("DispatchThread >> RC2 Key: "+new HexString(e.getKey().getEncoded()));
					    
						// Receive and decrypt message
					    byte[] message = Frame.get(socket).data;
					    System.out.println("DispatchThread >>  Received "+new HexString(message));
					    decryptTime = System.nanoTime();
						message = e.decrypt(message);
						decryptTime = System.nanoTime() - decryptTime;
					    System.out.println("DispatchThread >>  Message "+new HexString(message));
						
					    System.out.println("DispatchThread >> Iteration done.");
					}else throw new IllegalStateException("Unsupported crypto algorithm: "+enc);

				    // Send measured times
					System.out.println("DispatchThread >> Measured "+initTime +", "+decryptTime);
				    new DataOutputStream(socket.getOutputStream()).writeLong(initTime);
				    new DataOutputStream(socket.getOutputStream()).writeLong(decryptTime);
				    
				    // update progress bar
				    queueProgressBar.offer(i+1);
				    // set progress text
					queueText.offer("Iteration " + (i+1) + " of " + iterations + ": Decryption");
				}
			} catch (GeneralSecurityException | IOException e1) {
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
