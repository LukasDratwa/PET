package confcost.controller.ke;

import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Security;
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
	
	/**
	 * @param ke	The key exchange
	 * @return the registered name of the key exchange or null
	 */
	public static final @NonNull String getName(Class<? extends KeyExchange> ke) {
		return registeredKeyExchanges.get(ke);
	}
	
	protected KeyPair localKeyPair = null;
	
	protected PublicKey remotePubKey = null;
	
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
	 * @throws IOException If an IO error occurred
	 * @throws GeneralSecurityException	If a security error occurred
	 */
	public abstract void send(@NonNull Socket socket) throws IOException, GeneralSecurityException;
	
	/**
	 * Perform the key exchange on the specified socket on the application's receiving side.
	 * 
	 * @param socket	The socket
	 * @throws IOException If an IO error occurred
	 * @throws GeneralSecurityException	If a security error occurred
	 */
	public abstract void receive(@NonNull Socket socket) throws IOException, GeneralSecurityException;
	
	/**
	 * Sets the key length to the specified number of bit.
	 * @param bit	The length in bit
	 */
	public abstract void setKeyLength(int bit);
	
	/**
	 * Returns the local {@link KeyPair}
	 * @return	the {@link KeyPair} or <code>null</code>
	 */
	public KeyPair getLocalKeyPair() {
		return this.localKeyPair;
	}
	
	/**
	 * @return	the key
	 */
	public abstract byte[] getKey();
}
