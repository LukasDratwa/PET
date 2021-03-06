package confcost.controller.algorithm;

import java.util.List;
import java.security.Security;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.jdt.annotation.NonNull;

/**
 * Superclass for all {@link Encryption} and {@link Signature} algorithms.
 * 
 * @author Marc Eichler
 *
 */
public abstract class Algorithm {
	/**
	 * The registered {@link Algorithm}s
	 */
	private static final @NonNull List<@NonNull Class<? extends Algorithm>> REGISTERED_ALGORITHMS = new LinkedList<>();
	
	/**
	 * The names of the registered {@link Algorithm}s
	 */
	private static final @NonNull Map<@NonNull Class<? extends Algorithm>, @NonNull String> ALGORITHM_NAMES = 
			new HashMap<>(); 
	
	static {
		// Add security provider
		Security.addProvider(new BouncyCastleProvider());

		// Register encryptions
		register(AESEncryption.class, 	AESEncryption.NAME);
		register(DESEncryption.class, 	DESEncryption.NAME);
		register(RC2Encryption.class, 	RC2Encryption.NAME);
		register(RSAEncryption.class, 	RSAEncryption.NAME);
		register(ECIESEncryption.class,	ECIESEncryption.NAME);
		
		// Register signature algorithms
		register(SHA1Signature.class,		SHA1Signature.NAME);
		register(SHA256Signature.class,		SHA256Signature.NAME);
	}

	/**
	 * Registers an {@link Algorithm}.
	 * @param algorithm	The {@link Algorithm}
	 * @param name	The name
	 */
	public static void register(final @NonNull Class<? extends Algorithm> algorithm, final @NonNull String name) {
		REGISTERED_ALGORITHMS.add(algorithm);
		ALGORITHM_NAMES.put(algorithm, name);
	}
	
	/**
	 * @return all registered {@link Algorithm}s. 
	 */
	public static final @NonNull Iterable<Class<? extends Algorithm>> getRegisteredAlgorithms() {
		return REGISTERED_ALGORITHMS;
	}
	
	/**
	 * Returns the registered name for the {@link Algorithm}.
	 * @param algorithm	The {@link Algorithm}
	 * @return	The registered name, or <code>null</code>
	 */
	public static final @NonNull String getName(Class<? extends Algorithm> algorithm) {
		return ALGORITHM_NAMES.get(algorithm);
	}

	/**
	 * The default security provider
	 */
	private static final String DEFAULT_PROVIDER = "BC";

	/**
	 * The security provider
	 */
	protected final @NonNull String provider = DEFAULT_PROVIDER;
}
