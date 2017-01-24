package confcost.controller.encryption;

import java.security.GeneralSecurityException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.jdt.annotation.NonNull;

public abstract class Encryption {
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	protected final @NonNull String algorithm;
	protected final @NonNull String provider;
	
	public Encryption(final @NonNull String algorithm, final @NonNull String provider) {
		this.algorithm = algorithm;
		this.provider = provider;
	}

	public abstract @NonNull byte[] decrypt(final @NonNull byte @NonNull[] message) throws GeneralSecurityException;

	public abstract @NonNull byte[] encrypt(final @NonNull byte @NonNull[] message) throws GeneralSecurityException;
}
