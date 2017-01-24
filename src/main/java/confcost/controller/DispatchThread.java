package confcost.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;

import confcost.controller.encryption.AESEncryption;
import confcost.controller.encryption.AsymmetricEncryption;
import confcost.controller.encryption.RSAEncryption;
import confcost.controller.encryption.SymmetricEncryption;
import confcost.controller.ke.KeyExchange;
import confcost.controller.ke.KeyExchangeFactory;
import confcost.model.CProtocol;
import confcost.model.KEProtocol;
import confcost.network.Frame;
import confcost.util.HexString;

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
//			KeyExchange ke = KeyExchangeFactory.get(keyEx);
//			AESEncryption e = new AESEncryption(ke);
//			e.receive(socket);
			
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
				    System.out.println("DispatchThread >> AES Key: "+new HexString(e.getKey().getEncoded()));
				    initTime = System.nanoTime() - initTime;
				    
					// Receive and decrypt message
				    byte[] message = Frame.get(socket).data;
				    System.out.println("DispatchThread >>  Received "+new HexString(message));
				    decryptTime = System.nanoTime();
					message = e.decrypt(message);
					decryptTime = System.nanoTime() - decryptTime;
				    System.out.println("DispatchThread >>  Message "+new HexString(message));
					
				    System.out.println("DispatchThread >> Iteration done.");
				} else throw new IllegalStateException("Unsupported crypto algorithm: "+enc);
	
			    // Send measured times
				System.out.println("DispatchThread >> Measured "+initTime +", "+decryptTime);
			    new DataOutputStream(socket.getOutputStream()).writeLong(initTime);
			    new DataOutputStream(socket.getOutputStream()).writeLong(decryptTime);
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
	}
}
