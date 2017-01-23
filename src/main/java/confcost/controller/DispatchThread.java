package confcost.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import confcost.controller.encryption.AsymmetricEncryption;
import confcost.controller.encryption.RSAEncryption;
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
			final String enc = Frame.get(socket).toString();
			System.out.println("DispatchThread >> "+keyEx+"|"+enc);

			final int keyLength = new DataInputStream(socket.getInputStream()).readInt();
			System.out.println("DispatchThread >> Key length: "+keyLength);
//			KeyExchange ke = KeyExchangeFactory.get(keyEx);
//			AESEncryption e = new AESEncryption(ke);
//			e.receive(socket);
			
			AsymmetricEncryption e = new RSAEncryption("BC");
			
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
		} catch (IOException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		// Close the socket
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
