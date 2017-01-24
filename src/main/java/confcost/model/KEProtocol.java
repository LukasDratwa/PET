
package confcost.model;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Enumerates key exchange protocols.
 * 
 * @author Marc Eichler, Lukas Dratwa
 */
public enum KEProtocol {
	None("None"), DiffieHellman("DH");
	
	private final String name;
	
	private KEProtocol(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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