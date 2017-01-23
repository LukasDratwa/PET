package confcost.controller.ke;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import org.eclipse.jdt.annotation.NonNull;

/**
 * The abstract superclass for all key exchange implementations.
 * 
 * @author Marc Eichler
 *
 */
public abstract class KeyExchange {
	
	protected KeyPair localKeyPair = null;
	
	protected PublicKey remotePubKey = null;

	/**
	 * Perform the key exchange on the specified socket on the application's sending side.
	 * 
	 * @param socket	The socket
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidParameterSpecException 
	 */
	public abstract void send(@NonNull Socket socket) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, InvalidKeySpecException, InvalidParameterSpecException;
	
	/**
	 * Perform the key exchange on the specified socket on the application's receiving side.
	 * 
	 * @param socket	The socket
	 * @throws InvalidKeySpecException 
	 * @throws IOException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidParameterSpecException 
	 */
	public abstract void receive(@NonNull Socket socket) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, InvalidKeySpecException, InvalidParameterSpecException;
	
	/**
	 * Sets the key length.
	 */
	public abstract void setKeyLength(int bit);
	
	public KeyPair getLocalKeyPair() {
		return this.localKeyPair;
	}
	
	public abstract byte[] getKey();
}
