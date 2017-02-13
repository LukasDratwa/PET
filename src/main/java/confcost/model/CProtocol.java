package confcost.model;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Enumerates cryptographic protocols.
 * 
 * @author Marc Eichler, Lukas Dratwa
 */
public enum CProtocol {
	RSA("RSA"), AES("AES"), RC2("RC2"), ECIES("ECIES");
	
	private final String name;
	
	private CProtocol(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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