package confcost.controller;

import java.io.IOException;
import java.net.Socket;

import javax.crypto.Cipher;

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
			// Get message length
			int length = socket.getInputStream().read();
			
			byte[] encrypt = new byte[length];
			for (int i = 0; i < length; i++) {
				encrypt[i] = (byte)socket.getInputStream().read();
			}
			System.out.println("DispatchThread >> Received "+encrypt);
			
			byte[] message = sender.send(cipher.doFinal(message.getBytes("UTF-8")));

	        // Set cipher to decrypt
			Cipher cipher = Cipher.getInstance(transformation)
	        cipher.init(Cipher.DECRYPT_MODE, secretKey);


					System.out.println("Received: "+socket.getInputStream().read());
			System.out.println("DispatchThread >> Encrypted: "+message);
			
		} catch (IOException e) {
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
