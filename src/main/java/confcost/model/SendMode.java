package confcost.model;

import java.io.Serializable;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.algorithm.Encryption;
import confcost.controller.algorithm.Signature;
import confcost.controller.ke.KeyExchange;

/**
 * Represents a mode to send data. 
 * 
 * The SendMode is defined by its' key exchange protocol its message exchange protocol, and the key and message length.
 * 
 * @author Marc Eichler
 *
 */
public class SendMode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -41558251838781033L;
	
	/**
	 * The signature algorithm or <code>null</code>
	 */
	public final @Nullable Class<? extends Signature> signature;
	
	/**
	 * The key exchange protocol or <code>null</code>
	 */
	public final @Nullable Class<? extends KeyExchange> keyExchange;
	
	/**
	 * The encryption
	 */
	public final @NonNull Class<? extends Encryption> encryption;
	
	/**
	 * The message length in bit
	 */
	public final int messageLength;
	
	/**
	 * The key length in bit
	 */
	public final int keyLength;
	
	/**
	 * The number of iterations
	 */
	public final int iterations;
	
	/**
	 * Whether or not to generate a new key every iteration
	 */
	public final boolean generateKeyEveryIteration;
	
	/**
	 * Creates a new {@link SendMode}
	 * 
	 * @param signature		The signature protocol or <code>null</code>
	 * @param encryption	The encryption
	 * @param keyExchange	The key exchange protocol or <code>null</code>
	 * @param keyLength	The key length in bit
	 * @param messageLength	The message length in bit
	 */
	public SendMode(final @NonNull Class<? extends Signature> signature,
			final @NonNull Class<? extends Encryption> encryption,
			final @Nullable Class<? extends KeyExchange> keyExchange,
			final int keyLength,
			final int messageLength,
			final int iterations,
			final boolean generateKeyEveryIteration) {
		this.signature = signature;
		this.keyExchange = keyExchange;
		this.encryption = encryption;
		this.keyLength = keyLength;
		this.messageLength = messageLength;
		this.iterations = iterations;
		this.generateKeyEveryIteration = generateKeyEveryIteration;
	}
	
	@Override
	public final @NonNull String toString() {
		return Encryption.getName(this.encryption)+"/"+KeyExchange.getName(this.keyExchange)
		+" ("+this.keyLength+","+this.messageLength+") ";
	}
}

