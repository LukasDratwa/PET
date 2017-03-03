package confcost.controller.encryption;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.ke.KeyExchange;
import confcost.model.SendMode;

/**
 * Provides the interface for symmetric encryption methods.
 * 
 * @author Marc Eichler
 *
 */
public abstract class SymmetricEncryption extends Encryption {
	protected Key key;
	
	protected @NonNull KeyExchange keyExchange;
	
	/**
	 * Create a new {@link AsymmetricEncryption} with the specified key exchange protocol
	 * 
	 * @param ke
	 */
	public SymmetricEncryption(final @NonNull SendMode mode) {
		super(mode);
		
		// Initialize KeyExchange
		Constructor<? extends KeyExchange> c;
		try {
			c = mode.keyExchange.getConstructor((Class<?>[])null);
			keyExchange = c.newInstance((Object[])null);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public KeyExchange getKeyExchange() {
		return this.keyExchange;
	}
	
	/**
	 * 
	 * @param bitLength
	 */
	public void generateKey(final int bitLength, final @NonNull byte[] secret) {
		this.key = new SecretKeySpec(shortenKey(keyExchange.getKey(), bitLength), this.getAlgorithm());
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
