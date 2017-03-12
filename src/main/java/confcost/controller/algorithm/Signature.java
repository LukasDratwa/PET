package confcost.controller.algorithm;

import java.lang.reflect.Constructor;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;
import confcost.view.send.AlgorithmConfiguration;
import confcost.view.send.AlgorithmConfigurationFactory;

/**
 * Superclass for signature algorithms.
 * 
 * <p>
 * To add an additional signature algorithm, the following steps must be taken:
 * 1. Implement a new subclass of {@link Signature}
 * 2. Register this subclass to {@link Signature} (see {@link Signature#register(Class, Collection)})
 * 3. Register this subclass to {@link Algorithm} (see {@link Algorithm#register(Class, String)})
 * 4. (optional) Create a subclass of {@link AlgorithmConfiguration} or expand the functionality
 * 	of an existing subclass and add the creation logic to 
 * {@link AlgorithmConfigurationFactory#create(Class, confcost.model.Model)}
 * </p>
 * 
 * @author Marc Eichler
 *
 */
public abstract class Signature extends Algorithm {

	/**
	 * The registered encryption algorithms
	 */
	private static Map<@NonNull Class<? extends Signature>, @NonNull Collection<Class<? extends Encryption>>> availableEncryptions;

	static {
		availableEncryptions = new HashMap<>();
		
		// Register all encryptions to be used
		register(SHA1Signature.class,		SHA1Signature.ENCRYPTIONS);
		register(SHA256Signature.class,		SHA256Signature.ENCRYPTIONS);
	}
	
	/**
	 * Registers an encryption.
	 * @param signature The {@link Signature}
	 * @param name	A unique, printable name
	 * @param encryptions	The available encryptions
	 */
	public static void register(Class<? extends Signature> signature, 
			final @NonNull Collection<Class<? extends Encryption>> encryptions) {
		System.out.println("Registering Signature " + signature);
		availableEncryptions.put(signature, encryptions);
	}
	
	/**
	 * Returns the registered encryptions for the specified {@link Signature}.
	 * @param signature	The {@link Signature}
	 * @return	The encryptions
	 */
	public static final @NonNull Collection<Class<? extends Encryption>> getEncryptions(
			final @NonNull Class<? extends Signature> signature) {
		return availableEncryptions.get(signature);
	}
	
	/**
	 * The {@link Encryption} to be used
	 */
	protected final @NonNull Encryption encryption; 
	
	/**
	 * Creates a new {@link Signature}
	 * @param mode
	 * @throws ReflectiveOperationException
	 */
	public Signature(@NonNull SendMode mode) throws ReflectiveOperationException {
		// Initialize Encryption
		Constructor<? extends Encryption> e;
		e = mode.encryption.getConstructor(SendMode.class);
		encryption = e.newInstance(mode);
	}

	/**
	 * Signs the data
	 * @param data	The data
	 * @return	The signed data
	 * @throws InvalidKeyException 
	 * @throws GeneralSecurityException 
	 */
	public abstract @NonNull byte[] sign(final @NonNull byte[] data) throws InvalidKeyException, GeneralSecurityException;
	
	/**
	 * Verifies the data
	 * @param data	The data
	 * @param signature	The signature
	 * @return	true iff verified
	 * @throws GeneralSecurityException 
	 */
	public abstract boolean verify(final @NonNull byte[] data, final @NonNull byte[] signature) throws GeneralSecurityException;
	
	/**
	 * @return the {@link Encryption}
	 */
	public final @NonNull Encryption getEncryption() {
		return this.encryption;
	}
}
