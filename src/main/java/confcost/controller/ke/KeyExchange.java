package confcost.controller.ke;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The abstract superclass for all key exchange implementations.
 * 
 * <p>
 * To add an additional key exchange protocol, the following steps must be taken:
 * 1. Implement a new subclass of {@link KeyExchange}
 * 2. Register this subclass to {@link KeyExchange} (see {@link KeyExchange#register(Class, String)})
 * </p>
 * 
 * @author Marc Eichler
 *
 */
public abstract class KeyExchange {
	static {
		Security.addProvider(new BouncyCastleProvider());
		
		registeredKeyExchanges = new HashMap<>();
		
		// Register all key exchange protocols to be used
		register(NoKeyExchange.class, NoKeyExchange.NAME);
		register(DiffieHellmanKeyExchange.class, DiffieHellmanKeyExchange.NAME);
	}
	
	private static Map<Class<? extends KeyExchange>, @NonNull String> registeredKeyExchanges;
	private static void register(Class<? extends KeyExchange> ke, String name) {
		registeredKeyExchanges.put(ke, name);
	}
	
	public final static @NonNull Iterable<Class<? extends KeyExchange>> getRegisteredKeyExchanges() {
		return registeredKeyExchanges.keySet();
	}
	
	protected KeyPair localKeyPair = null;
	
	protected PublicKey remotePubKey = null;
	
	public static final @NonNull String getName(Class<? extends KeyExchange> ke) {
		return registeredKeyExchanges.get(ke);
	}
	
	/**
	 * Returns the name of the key exchange protocol.
	 *  
	 * @return	the name
	 */
	public abstract String getName();

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
