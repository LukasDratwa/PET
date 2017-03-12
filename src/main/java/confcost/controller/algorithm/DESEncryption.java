package confcost.controller.algorithm;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;

/**
 * Bouncy Castle DES Encryption
 * 
 * @author Marc Eichler
 *
 */
public class DESEncryption extends SymmetricEncryption {
	/**
	 * A printable, unique name of the algorithm
	 */
	public static final @NonNull String NAME = "DES";
	
	/**
	 * The supported key length
	 */
	public static final @NonNull Integer[] KEY_LENGTHS = { 64 };
	
	@Override
	public @NonNull String getName() {
		return NAME;
	}
	
	/**
	 * Constructs an {@link DESEncryption} based on the specified {@link SendMode}.
	 * @param sendMode	The {@link SendMode}
	 */
	public DESEncryption(final @NonNull SendMode sendMode) {
		super(sendMode);
	}
	
	@Override
	public @NonNull byte[] encrypt(@NonNull byte @NonNull [] message) throws GeneralSecurityException {
		if (this.secretKey == null) throw new IllegalStateException("No key set!");

		Cipher cipher = Cipher.getInstance(NAME, this.provider);
		cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
		return cipher.doFinal(message);
	}
	
	@Override
	public @NonNull byte[] decrypt(@NonNull byte @NonNull [] message) throws GeneralSecurityException {
		if (this.secretKey == null) throw new IllegalStateException("No key set!");

		Cipher cipher = Cipher.getInstance(NAME, this.provider);
		cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
		return cipher.doFinal(message);
	}
}
