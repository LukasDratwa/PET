package confcost.model;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Enumerates cryptographic protocols.
 * 
 * @author Marc Eichler
 *
 */
public enum CProtocol {
	RSA, AES;
	
	public String getName() {
		switch (this) {
		case RSA:	return "RSA";
		case AES:	return "AES";
		default:	return null;
		}
	}
	
	/**
	 * Returns the {@link CProtocol} with the specified name.
	 * 
	 * @param name	The name
	 * @return the matching {@link CProtocol}
	 */
	public static CProtocol get(@NonNull String name) {
		for (CProtocol p : CProtocol.values())
			if (p != null && p.getName().equals(name)) return p;
		return null;
	}
}
