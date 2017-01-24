package confcost.controller;

import java.io.DataInputStream;
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
			System.out.println("DispatchThread >> "+keyEx+"|"+enc);

			final int keyLength = new DataInputStream(socket.getInputStream()).readInt();
			System.out.println("DispatchThread >> Key length: "+keyLength);
//			KeyExchange ke = KeyExchangeFactory.get(keyEx);
//			AESEncryption e = new AESEncryption(ke);
//			e.receive(socket);
			
			if (enc == CProtocol.RSA) {
				AsymmetricEncryption e = new RSAEncryption(DEFAULT_PROVIDER);
				
				// Generate and send public key
				e.generateKeyPair(keyLength);
				System.out.println("DispatchThread >> Sending public key: "+keyLength);
				new Frame(e.getPublicKey().getEncoded()).write(socket);;
				
				// Retrieve and decrypt message
				byte[] message = Frame.get(socket).data;
				System.out.println("DispatchThread >> Received: "+new HexString(message));
				
				message = e.decrypt(message);
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
				e.generateKey(keyLength, ke.getKey());
			    System.out.println("DispatchThread >> AES Key: "+new HexString(e.getKey().getEncoded()));
			    
				// Generate and encrypt message
			    byte[] message = Frame.get(socket).data;
			    System.out.println("DispatchThread >>  Received "+new HexString(message));
				message = e.decrypt(message);
			    System.out.println("DispatchThread >>  Message "+new HexString(message));
				
				// Send message
			    System.out.println("DispatchThread >> Done.");
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
