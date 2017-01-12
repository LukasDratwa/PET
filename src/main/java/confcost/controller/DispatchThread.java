package confcost.controller;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import confcost.controller.encryption.RSAEncryption;
import confcost.model.KEProtocol;
import confcost.network.Frame;

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
			KEProtocol keyEx = KEProtocol.get(Frame.get(socket).toString()); // Ignored for now
			String enc = Frame.get(socket).toString();
			System.out.println("DispatchThread >> "+keyEx+"|"+enc);
			
//			KeyExchange ke = KeyExchangeFactory.get(keyEx);
//			AESEncryption e = new AESEncryption(ke);
//			e.receive(socket);
			
			RSAEncryption e = new RSAEncryption();
			e.receive(socket);
			
			System.out.println("DispatchThread >> Done.");
		} catch (IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchProviderException | InvalidKeySpecException | InvalidParameterSpecException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
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
