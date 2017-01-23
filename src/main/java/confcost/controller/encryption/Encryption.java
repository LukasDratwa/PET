package confcost.controller.encryption;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public abstract class Encryption {
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
}
