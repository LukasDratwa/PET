package confcost.controller.encryption;

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

import org.eclipse.jdt.annotation.NonNull;

public abstract class Encryption {
	/**
	 * Perform the encryption method as sender.
	 * 
	 * @param socket	The socket to the receiver
	 * @param messageLength	The message length
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeySpecException
	 * @throws InvalidParameterSpecException
	 * @throws IOException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public abstract void send(@NonNull Socket socket, int messageLength) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidParameterSpecException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;
	
	/**
	 * Perform the encryption methods as receiver.
	 * 
	 * @param socket	The socket to the sender
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeySpecException
	 * @throws InvalidParameterSpecException
	 * @throws IOException
	 */
	public abstract void receive(@NonNull Socket socket) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidParameterSpecException, IOException;
}
