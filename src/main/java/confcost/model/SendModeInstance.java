package confcost.model;

import org.eclipse.jdt.annotation.NonNull;

/**
 * An instance of a {@link SendMode}. 
 * 
 * <p>This class is used to represent a run of a {@link SendMode}. 
 * It is defined by its {@link SendMode}, the key length and its message length. 
 * </p>
 * 
 * @author Marc Eichler
 *
 */
public class SendModeInstance {
	private final @NonNull SendMode mode;
	
	private final int keyLength;
	private final int messageLength;
	
	public SendModeInstance(SendMode mode, int keyLength, int messageLength) {
		this.mode = mode;
		this.keyLength = keyLength;
		this.messageLength = messageLength;
	}
	
	public int getKeyLength() {
		return this.keyLength;
	}
	
	public int getMessageLength() {
		return this.messageLength;
	}
	
	public SendMode getSendMode() {
		return this.mode;
	}
}
