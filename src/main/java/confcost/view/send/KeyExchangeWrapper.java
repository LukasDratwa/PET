package confcost.view.send;

import javax.swing.JComboBox;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.ke.KeyExchange;

/**
 * Wraps a {@link KeyExchange} for use in a {@link JComboBox}
 * 
 * @author Marc Eichler
 *
 */
class KeyExchangeWrapper {
	/**
	 * The wrapped {@link KeyExchange}
	 */
	public final Class<? extends KeyExchange> ke;
	
	/**
	 * The {@link KeyExchange}s' registered name
	 */
	private final String name;
	
	/**
	 * Constructor
	 * @param ke	The {@link KeyExchange}
	 */
	KeyExchangeWrapper(final Class<? extends KeyExchange> ke) {
		this.ke = ke;
		this.name = KeyExchange.getName(ke);
	}
	
	public final @NonNull Class<? extends KeyExchange> get() {
		return this.ke;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}