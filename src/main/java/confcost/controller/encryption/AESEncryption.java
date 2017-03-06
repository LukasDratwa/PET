package confcost.controller.encryption;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;

/**
 * Bouncy Castle AES Encryption
 * 
 * @author Marc Eichler
 *
 */
public class AESEncryption extends SymmetricEncryption {
	/**
	 * A printable, unique name of the algorithm
	 */
	public static final @NonNull String NAME = "AES";
	
	/**
	 * The supported key length
	 */
	public static final @NonNull Integer[] KEY_LENGTHS = { 128, 192, 256 };
	
	@Override
	public @NonNull String getAlgorithm() {
		return NAME;
	}
	
	/**
	 * Constructs an {@link AESEncryption} based on the specified {@link SendMode}.
	 * @param sendMode	The {@link SendMode}
	 */
	public AESEncryption(final @NonNull SendMode sendMode) {
		super(sendMode);
	}
	
	@Override
	public @NonNull byte[] encrypt(@NonNull byte @NonNull [] message) throws GeneralSecurityException {
		if (this.key == null) throw new IllegalStateException("No key set!");

		Cipher cipher = Cipher.getInstance(NAME, this.provider);
		cipher.init(Cipher.ENCRYPT_MODE, this.key);
		return cipher.doFinal(message);
	}
	
	@Override
	public @NonNull byte[] decrypt(@NonNull byte @NonNull [] message) throws GeneralSecurityException {
		if (this.key == null) throw new IllegalStateException("No key set!");

		Cipher cipher = Cipher.getInstance(NAME, this.provider);
		cipher.init(Cipher.DECRYPT_MODE, this.key);
		return cipher.doFinal(message);
	}
}
