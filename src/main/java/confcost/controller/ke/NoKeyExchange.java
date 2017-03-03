package confcost.controller.ke;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Random;

import org.eclipse.jdt.annotation.NonNull;

import confcost.network.Frame;

/**
 * Implements functionality to exchange keys if no key exchange protocol was specified.
 * 
 * <p>
 * {@link NoKeyExchange} simply sends any required keys to the communications partner unencrypted.
 * </p>
 * 
 * @author Marc Eichler
 *
 */
public class NoKeyExchange extends KeyExchange {
	public static final String NAME = "<none>";
	
	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * Length of the secret in bit
	 */
	private int keyLength = 0;
	
	/**
	 * The shared "secret"
	 */
	private byte[] secretKey;
	
	@Override
	public void send(@NonNull Socket socket)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException,
			InvalidKeyException, IOException, InvalidKeySpecException, InvalidParameterSpecException {
		if (keyLength <= 0) throw new IllegalStateException("No key length defined!");
	    
		// Send key length
		new DataOutputStream(socket.getOutputStream()).writeInt(keyLength);

		System.out.println("NoKeyExchange::send(Socket) >> Generating Secret");
		secretKey = generateSecret(keyLength);
		
		// Send public key
		new Frame(secretKey).write(socket);
	}

	@Override
	public void receive(@NonNull Socket socket)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException,
			InvalidKeyException, IOException, InvalidKeySpecException, InvalidParameterSpecException {
		keyLength = new DataInputStream(socket.getInputStream()).readInt();
		
		secretKey = Frame.get(socket).data;
	}

	@Override
	public void setKeyLength(int bit) {
		if (bit <= 0) throw new IllegalArgumentException("Invalid bit length: "+bit);
		keyLength = bit;
	}

	@Override
	public byte[] getKey() {
		return secretKey;
	}
	
	/**
	 * Generates a new secret of specified length
	 * 
	 * @param length	The length in bit
	 * @return
	 */
	public byte[] generateSecret(int length) {
		int byteLength = (int) Math.ceil(length/8);
	    byte[] secret = new BigInteger(length, new Random()).toByteArray();
	    
	    // Length is correct, return message
	    if (secret.length == byteLength) return secret;

	    // BigInteger may have created one byte to much or several too few. Fix it.
	    byte[] correctSecret = new byte[byteLength];
	    for (int i = 0; i < byteLength; ++i) {
	    	if (i < secret.length)
	    		correctSecret[i] = secret[i];
	    	else correctSecret[i] = 0;
	    }
	    return correctSecret;
	}
}
