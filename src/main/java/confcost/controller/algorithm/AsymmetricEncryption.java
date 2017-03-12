package confcost.controller.algorithm;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.model.SendMode;

/**
 * Provides support for generic asymmetric encryptions.
 * 
 * @author Marc Eichler
 *
 */
public abstract class AsymmetricEncryption extends Encryption {
	/**
	 * The {@link PublicKey}
	 */
	protected PublicKey publicKey;
	
	/**
	 * The {@link PrivateKey}
	 */
	protected PrivateKey privateKey;
	
	/**
	 * Constructor
	 * 
	 * @param mode	The {@link SendMode}
	 */
	public AsymmetricEncryption(final @NonNull SendMode mode) {
		super(mode);
	}

	/**
	 * Sets the {@link PrivateKey}.
	 * @param key	the {@link Key}
	 */
	public void setPrivateKey(final @NonNull PrivateKey key) {
		this.privateKey = key;
	}
	
	/**
	 * @return	the {@link PrivateKey}
	 */
	public final @Nullable PrivateKey getPrivateKey() {
		return this.privateKey;
	}
	
	/**
	 * Sets the {@link PublicKey}.
	 * @param bytes The public key as bytes
	 * @throws GeneralSecurityException	If a security error occurred
	 * @throws IOException If an IO error occurred
	 */
	public void setPublicKey(byte[] bytes) throws GeneralSecurityException, IOException {
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(bytes);
		this.publicKey = KeyFactory.getInstance(getName(), this.provider).generatePublic(pubKeySpec);
	}

	/**
	 * @return the {@link PublicKey}.
	 */
	public PublicKey getPublicKey() {	
		return this.publicKey;
	}
	
	/**
	 * Generates a {@link KeyPair} of the specified bit length.
	 * 
	 * @param keyLength	The key length in bit
	 * @throws GeneralSecurityException If a security error occured
	 */
	public abstract void generateKeyPair(final int keyLength) throws GeneralSecurityException;
	
	@Override
	public byte[] encrypt(@NonNull final byte[] message) throws GeneralSecurityException {
		if (this.publicKey == null) throw new IllegalStateException("No public key set!");
		
		System.out.println("AsymmetricEncryption::encrypt >> Encrypting message of "+message.length+" byte");
		
		Cipher cipher = Cipher.getInstance(getName(), this.provider);
		cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
		
		return cipher.doFinal(message);
	}
	
	@Override
	public byte[] decrypt(@NonNull final byte[] message) throws GeneralSecurityException {
		if (this.privateKey == null) throw new IllegalStateException("No private key set!");
		
		System.out.println("GAE::decrypt >> Decrypting");
		Cipher cipher = Cipher.getInstance(getName(), this.provider);
		cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
		return cipher.doFinal(message);
	}
}
