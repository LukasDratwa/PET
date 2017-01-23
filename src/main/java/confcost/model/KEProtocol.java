
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
	 * Return the protocol as a unique string identifier
	 * @return
	 */
	public String getName() {
		switch (this) {
		case None: return "None";
		case DiffieHellman:	return "DH";
		default:	return null;
		}
	}
	
	/**
	 * 
	 */
	public static KEProtocol get(@NonNull String name) {
		for (KEProtocol p : KEProtocol.values())
			if (p.getName().equals(name)) return p;
		return null;
	}
}
