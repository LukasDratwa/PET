package confcost.controller.encryption;

import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;
import confcost.util.HexString;

/**
 * Provides support for generic asymmetric encryptions.
 * 
 * @author Marc Eichler
 *
 */
public abstract class AsymmetricEncryption extends Encryption {
	protected PublicKey publicKey;
	protected PrivateKey privateKey;
	
	/**
	 * Constructor
	 * 
	 * @param provider	The security provider
	 */
	public AsymmetricEncryption(final @NonNull SendMode mode) {
		super(mode);
	}
	
	/**
	 * Sets the {@link PublicKey}.
	 * 
	 * @param bytes The public key as bytes
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public void setPublicKey(byte[] bytes) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(bytes);
		this.publicKey = KeyFactory.getInstance(getAlgorithm(), this.provider).generatePublic(pubKeySpec);
		System.out.println("GAE::setPublicKey >> Setting PubKey " + new HexString(bytes));
	}
	
	/**
	 * Generates a {@link KeyPair} of the specified bit length.
	 * 
	 * @param keyLength	The key length in bit
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidAlgorithmParameterException
	 */
	public abstract void generateKeyPair(int keyLength) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException;
	
	@Override
	public byte[] encrypt(@NonNull final byte[] message) throws GeneralSecurityException {
		if (this.publicKey == null) throw new IllegalStateException("No public key set!");
		
		System.out.println("AsymmetricEncryption::encrypt >> Encrypting message of "+message.length+" byte");
		
		Cipher cipher = Cipher.getInstance(getAlgorithm(), this.provider);
		cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
		
		return cipher.doFinal(message);
	}
	
	@Override
	public byte[] decrypt(@NonNull final byte[] message) throws GeneralSecurityException {
		if (this.privateKey == null) throw new IllegalStateException("No private key set!");
		
		System.out.println("GAE::decrypt >> Decrypting");
		Cipher cipher = Cipher.getInstance(getAlgorithm(), this.provider);
		cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
		return cipher.doFinal(message);
	}
	
	/**
	 * @return the {@link PublicKey}.
	 */
	public PublicKey getPublicKey() {
		return this.publicKey;
	}
}
