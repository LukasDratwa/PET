package confcost.model;

import java.io.Serializable;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Represents a mode to send data. The SendMode is defined by its' key exchange protocol and its message exchange protocol.
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
	public final @Nullable KEProtocol keyExchange;
	
	/**
	 * The message exchange protocol
	 */
	public final @NonNull CProtocol messageExchange;
	
	/**
	 * Creates a new {@link SendMode}
	 * 
	 * @param keyExchange	The key exchange protocol
	 * @param messageExchange	The message exchange protocol
	 */
	public SendMode(@Nullable KEProtocol keyExchange, @NonNull CProtocol messageExchange) {
		this.keyExchange = keyExchange;
		this.messageExchange = messageExchange;
	}
	
	/**
	 * Returns a new instance of this {@link SendMode}.
	 * 
	 * @param keyLength
	 * @param messageLength
	 * @return
	 */
	public SendModeInstance getInstance(int keyLength, int messageLength) {
		return new SendModeInstance(this, keyLength, messageLength);
	}
}
