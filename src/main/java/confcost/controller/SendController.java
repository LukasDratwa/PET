package confcost.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.CProtocol;
import confcost.model.SendModeInstance;

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
	
	public void send(SendModeInstance instance) throws UnknownHostException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		System.out.println("SendController >> Sending "+instance+" to "+host+":"+port);
		Socket socket = new Socket(host, port);
	    socket.setSoTimeout(1000);
		System.out.println("SendController >> Connected.");
		
		// Generate message
	    byte[] message = new BigInteger(instance.getMessageLength(), new Random()).toByteArray();
	    System.out.println("SendController >> Generated message '"+message+"'");
	    
	    SecretKey secretKey = KeyGenerator.getInstance(CProtocol.RSA.getName()).generateKey();
        Cipher cipher = Cipher.getInstance(instance.getSendMode().messageExchange.getName());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        System.out.println("SendController >> Encrypting...");
        byte[] encrypt = cipher.doFinal(message);
        
        System.out.println("SendController >> Sending "+encrypt);
        // Encrypt and send
        socket.getOutputStream().write(encrypt.length);
        socket.getOutputStream().write(encrypt);
        
        System.out.println("SendController >> Done.");
        
        socket.close();
	}
}
