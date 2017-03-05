package confcost.model;

import java.io.Serializable;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.encryption.Encryption;
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
	 * The key exchange protocol. Can be null.
	 */
	public final @Nullable Class<? extends KeyExchange> keyExchange;
	
	/**
	 * The message exchange protocol
	 */
	public final @NonNull Class<? extends Encryption> messageExchange;
	
	public final int messageLength;
	
	/**
	 * 
	 */
	public final int keyLength;
	
	/**
	 * Creates a new {@link SendMode}
	 * 
	 * @param messageExchange	The message exchange protocol
	 * @param keyExchange	The key exchange protocol or <code>null</code>
	 * @param keyLength	The key length in bit
	 * @param messageLength	The message length in byte
	 */
	public SendMode(final @NonNull Class<? extends Encryption> messageExchange,
			final @Nullable Class<? extends KeyExchange> keyExchange,
			final int keyLength,
			final int messageLength) {
		this.keyExchange = keyExchange;
		this.messageExchange = messageExchange;
		this.keyLength = keyLength;
		this.messageLength = messageLength;
	}
	
	/**
	 * Returns a new instance of this {@link SendMode}.
	 * 
	 * @param keyLength	The key length
	 * @param messageLength	The message length
	 * @return	The {@link SendModeInstance}
	 */
	public SendModeInstance getInstance(int keyLength, int messageLength) {
		return new SendModeInstance(this, keyLength, messageLength);
	}
}

