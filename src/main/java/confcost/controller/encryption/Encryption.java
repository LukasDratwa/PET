package confcost.controller.encryption;

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
 * Generic superclass for encryption algorithms.
 * 
 * <p>
 * To add an additional encryption method, the following steps must be taken:
 * 1. Implement a new subclass of {@link Encryption}
 * 2. Register this subclass to {@link Encryption} (see {@link Encryption#register(Class, String, Integer[])})
 * 3. (optional) Create a subclass of {@link AlgorithmConfiguration} or expand the functionality
 * 	of an existing subclass and add the creation logic to 
 * {@link AlgorithmConfigurationFactory#create(Class, confcost.model.Model)}
 * </p>
 * 
 * @author Marc Eichler
 *
 */
public abstract class Encryption {
	static {
		// Add security provider
		Security.addProvider(new BouncyCastleProvider());

		registeredEncryptions = new HashMap<>();
		availableKeyLengths = new HashMap<>();
		
		// Register all encryptions to be used
		register(AESEncryption.class,	AESEncryption.NAME, 	AESEncryption.KEY_LENGTHS);
		register(ECIESEncryption.class,	ECIESEncryption.NAME, 	ECIESEncryption.KEY_LENGTHS);
		register(RC2Encryption.class,	RC2Encryption.NAME, 	RC2Encryption.KEY_LENGTHS);
		register(RSAEncryption.class,	RSAEncryption.NAME, 	RSAEncryption.KEY_LENGTHS);
	}
	
	/**
	 * The default security provider
	 */
	private static final String DEFAULT_PROVIDER = "BC";

	/**
	 * The registered encryptions
	 */
	private static Map<@NonNull Class<? extends Encryption>, @NonNull String> registeredEncryptions;

	/**
	 * The registered key length
	 */
	private static Map<@NonNull Class<? extends Encryption>, @NonNull Integer[]> availableKeyLengths;

	/**
	 * Registers an encryption.
	 * @param encryption	The {@link Encryption}
	 * @param name	A unique, printable name
	 * @param keyLengths	The available key lengths in bit
	 */
	public static void register(Class<? extends Encryption> encryption, 
			final @NonNull String name, final @NonNull Integer[] keyLengths) {
		System.out.println("Registering " + encryption + " ("+name+")");
		registeredEncryptions.put(encryption, name);
		availableKeyLengths.put(encryption, keyLengths);
	}
	
	/**
	 * @return all registered encryptions
	 */
	public static final Iterable<@NonNull Class<? extends Encryption>> getRegisteredEncryptions() {
		return registeredEncryptions.keySet();
	}
	
	/**
	 * Returns a printable name for the provided algorithm.
	 * 
	 * @param encryption	The algorithm
	 * @return	The printable name, or <code>null</code>
	 */
	public static String getName(final @NonNull Class<? extends Encryption> encryption) {
		return registeredEncryptions.get(encryption);
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
	 * The security provider
	 */
	protected final @NonNull String provider = DEFAULT_PROVIDER;
	
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
	protected abstract @NonNull String getAlgorithm();

	/**
	 * Decrypts the specified message
	 * @param message	The message
	 * @return	The decrypted message
	 * @throws GeneralSecurityException	If a security error occured
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
