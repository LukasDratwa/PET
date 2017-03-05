package confcost.controller.encryption;

import java.lang.reflect.Constructor;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.ke.KeyExchange;
import confcost.model.SendMode;

/**
 * Provides the interface for symmetric encryption methods.
 * 
 * @author Marc Eichler
 *
 */
public abstract class SymmetricEncryption extends Encryption {
	/**
	 * The currently set {@link Key}
	 */
	protected @Nullable Key key;
	
	/**
	 * The {@link KeyExchange}
	 */
	protected @NonNull KeyExchange keyExchange;
	
	/**
	 * Create a new {@link AsymmetricEncryption} with the specified key exchange protocol
	 * 
	 * @param mode	The {@link SendMode}
	 */
	public SymmetricEncryption(final @NonNull SendMode mode) {
		super(mode);
		
		// Initialize KeyExchange
		Constructor<? extends KeyExchange> c;
		try {
			c = mode.keyExchange.getConstructor((Class<?>[])null);
			keyExchange = c.newInstance((Object[])null);
		} catch (ReflectiveOperationException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the {@link KeyExchange}
	 */
	public final @NonNull KeyExchange getKeyExchange() {
		return this.keyExchange;
	}
	
	/**
	 * Generates an appropriate key based on the specified length and secret.
	 * @param bitLength	The length of the key in bit
	 * @param secret	The secret
	 */
	public void generateKey(final int bitLength, final @NonNull byte[] secret) {
		this.key = new SecretKeySpec(shortenKey(keyExchange.getKey(), bitLength), this.getAlgorithm());
	}
	
	/**
	 * Sets the {@link Key}.
	 * @param key	the {@link Key}
	 */
	public void setKey(Key key) {
		this.key = key;
	}
	
	/**
	 * @return	the {@link Key}
	 */
	public final @Nullable Key getKey() {
		return this.key;
	}
	
	/**
	 * Returns a the specified key, shortened to <code>length</code> bit.
	 * @param key	The key
	 * @param length	The length in bit
	 * @return	The shortened key
	 */
	private byte[] shortenKey(final byte[] key, int length) {
		final byte[] shortKey = new byte[length/8];
		
		System.arraycopy(key, 0,  shortKey, 0, shortKey.length);
		
		return shortKey;
	}
}
