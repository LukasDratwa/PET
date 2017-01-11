package confcost.controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.encryption.RSAEncryption;
import confcost.model.SendModeInstance;
import confcost.network.Frame;

/**
 * Responsible for sending data in accordance with a {@link SendModeInstance}.
 * 
 * @author Marc Eichler
 *
 */
public class SendController {

	private final @NonNull String host;
	private final @NonNull int port;
	
	/**
	 * Creates a new {@link SendController}.
	 * 
	 * @param host	The receiver host name or IP
	 * @param port	The receiver port
	 */
	public SendController(@NonNull String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void connect() throws UnknownHostException, IOException {
	}
	
	public void send(SendModeInstance instance) throws UnknownHostException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidParameterSpecException {
		System.out.println("SendController >> Sending "+instance+" to "+host+":"+port);
		Socket socket = new Socket(host, port);
	    socket.setSoTimeout(10000);
		System.out.println("SendController >> Connected.");
		
		new Frame(instance.getSendMode().keyExchange.getName()).write(socket);
		new Frame(instance.getSendMode().messageExchange.getName()).write(socket);
		
		// Get KeyExchange
//		KeyExchange ke = KeyExchangeFactory.get(instance.getSendMode().keyExchange);
//		ke.setKeyLength(instance.getKeyLength());
		
		// Perform key exchange and send message
//		AESEncryption e = new AESEncryption(ke);
//		e.send(socket, instance.getMessageLength());
		
		RSAEncryption e = new RSAEncryption();
		e.send(socket, instance.getMessageLength());
		
		socket.close();
		System.out.println("SendController >> Done.");
	}
}
