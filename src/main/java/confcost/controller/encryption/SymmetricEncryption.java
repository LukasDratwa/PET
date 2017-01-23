package confcost.controller.encryption;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

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
	
	protected Key key;
	
	/**
	 * Create a new {@link AsymmetricEncryption} with the specified key exchange protocol
	 * 
	 * @param ke
	 */
	public SymmetricEncryption(final @NonNull String algorithm, final @NonNull String provider, final @NonNull KeyExchange ke) {
		super(algorithm, provider);
		
		keyExchange = ke;
	}
	
	/**
	 * 
	 * @param bitLength
	 */
	public void generateKey(final int bitLength, final @NonNull byte[] secret) {
		this.key = new SecretKeySpec(shortenKey(keyExchange.getKey(), bitLength), this.algorithm);
	}
	
	public void setKey(Key key) {
		this.key = key;
	}
	
	public Key getKey() {
		return this.key;
	}
	
	private byte[] shortenKey(final byte[] key, int length) {
		final byte[] shortKey = new byte[length/8];
		
		System.arraycopy(key, 0,  shortKey, 0, shortKey.length);
		
		return shortKey;
	}
}
