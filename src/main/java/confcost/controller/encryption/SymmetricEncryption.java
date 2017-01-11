package confcost.controller.encryption;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.ke.KeyExchange;

/**
 * Provides the interface for symmetric encryption methods.
 * 
 * @author Marc Eichler
 *
 */
public abstract class SymmetricEncryption extends Encryption {
	protected final KeyExchange keyExchange;
	
	/**
	 * Create a new {@link AsymmetricEncryption} with the specified key exchange protocol
	 * 
	 * @param ke
	 */
	public SymmetricEncryption(@NonNull KeyExchange ke) {
		keyExchange = ke;
	}
}
