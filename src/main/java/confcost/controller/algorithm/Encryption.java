package confcost.controller.algorithm;

import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;
import confcost.view.send.AlgorithmConfiguration;
import confcost.view.send.AlgorithmConfigurationFactory;

/**
 * Superclass for encryption algorithms.
 * 
 * <p>
 * To add an additional encryption method, the following steps must be taken:
 * 1. Implement a new subclass of {@link Encryption}
 * 2. Register this subclass to {@link Encryption} (see {@link Encryption#register(Class, Integer[])})
 * 3. (optional) Create a subclass of {@link AlgorithmConfiguration} or expand the functionality
 * 	of an existing subclass and add the creation logic to 
 * {@link AlgorithmConfigurationFactory#create(Class, confcost.model.Model)}
 * </p>
 * 
 * @author Marc Eichler
 *
 */
public abstract class Encryption extends Algorithm {
	/**
	 * The registered key length
	 */
	private static Map<@NonNull Class<? extends Encryption>, @NonNull Integer[]> availableKeyLengths = new HashMap<>();
	
	static {
		// Add security provider
		Security.addProvider(new BouncyCastleProvider());
		
		// Register all encryptions to be used
		register(AESEncryption.class,	AESEncryption.KEY_LENGTHS);
		register(ECIESEncryption.class,	ECIESEncryption.KEY_LENGTHS);
		register(RC2Encryption.class,	RC2Encryption.KEY_LENGTHS);
		register(RSAEncryption.class,	RSAEncryption.KEY_LENGTHS);
	}
	
	/**
	 * Registers an encryption.
	 * @param encryption	The {@link Encryption}
	 * @param name	A unique, printable name
	 * @param keyLengths	The available key lengths in bit
	 */
	public static void register(Class<? extends Encryption> encryption, 
			final @NonNull Integer[] keyLengths) {
		System.out.println("Registering Encryption " + encryption);
		availableKeyLengths.put(encryption, keyLengths);
	}
	
	/**
	 * Returns the registered key lengths for the specified algorithm
	 * @param encryption	The algorithm
	 * @return	The key lengths
	 */
	public static Integer[] getKeyLength(final @NonNull Class<? extends Encryption> encryption) {
		return availableKeyLengths.get(encryption);
	}
	
	/**
	 * The {@link SendMode}
	 */
	protected final @NonNull SendMode mode;
	
	/**
	 * Constructor
	 * @param mode	The {@link SendMode}
	 */
	public Encryption(final @NonNull SendMode mode) {
		this.mode = mode;
	}
	
	/**
	 * @return	the name of the algorithm
	 */
	protected abstract @NonNull String getName();
	
	/**
	 * Decrypts the specified message
	 * @param message	The message
	 * @return	The decrypted message
	 * @throws GeneralSecurityException	If a security error occurred
	 */
	public abstract @NonNull byte[] decrypt(final @NonNull byte @NonNull[] message) throws GeneralSecurityException;

	/**
	 * Encrypts the specified message
	 * @param message	The message
	 * @return	The encrypted message
	 * @throws GeneralSecurityException	If a security error occured
	 */
	public abstract @NonNull byte[] encrypt(final @NonNull byte @NonNull[] message) throws GeneralSecurityException;
}
