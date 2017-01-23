
package confcost.model;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Enumerates key exchange protocols.
 * 
 * @author Marc Eichler
 *
 */
public enum KEProtocol {
	None, DiffieHellman;
	
	/**
	 * @return the protocol as a unique string identifier.
	 */
	public String getName() {
		switch (this) {
		case None: return "None";
		case DiffieHellman:	return "DH";
		default:	return null;
		}
	}
	
	/**
	 * Returns the {@link KEProtocol} with the specified name.
	 * 
	 * @param name	The name
	 * @return the matching {@link KEProtocol}
	 */
	public static KEProtocol get(@NonNull String name) {
		for (KEProtocol p : KEProtocol.values())
			if (p.getName().equals(name)) return p;
		return null;
	}
}
