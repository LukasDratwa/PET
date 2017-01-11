package confcost.controller.ke;

import org.eclipse.jdt.annotation.Nullable;

import confcost.model.KEProtocol;

public class KeyExchangeFactory {
	public static @Nullable KeyExchange get(KEProtocol ke) {
		switch (ke) {
		case None: return null;
		case DiffieHellman: return new DiffieHellmanKeyExchange();
		default: throw new IllegalArgumentException("Unknown key exchange method: "+ke);
		} 
	}
}
