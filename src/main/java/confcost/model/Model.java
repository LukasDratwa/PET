package confcost.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.algorithm.Algorithm;
import confcost.controller.ke.KeyExchange;
import confcost.model.statistics.CryptoPass;

/**
 * Main model class
 * 
 * @author Marc Eichler
 *
 */
public class Model {
	/**
	 * The enabled {@link Algorithm}s
	 */
	private final @NonNull List<Class<? extends Algorithm>> algorithms;
	
	/**
	 * The enabled {@link KeyExchange}s
	 */
	private final @NonNull List<Class<? extends KeyExchange>> keyExchanges;

	/**
	 * The {@link StatModel}
	 */
	private final @NonNull StatModel statModel = new StatModel();
	
	/**
	 * The {@link ConnectionModel}
	 */
	private final @NonNull ConnectionModel connectionModel = new ConnectionModel();
	
	/**
	 * The current software version
	 */
	private @Nullable String version = null;
	
	/**
	 * Create a new {@link Model}
	 */
	public Model() {
		algorithms = new LinkedList<>();
		keyExchanges = new LinkedList<>();

		// Add registered algorithms
		for (final @NonNull Class<? extends Algorithm> e : Algorithm.getRegisteredAlgorithms()) {
			this.algorithms.add(e);
		}
		
		// Add key exchanges
		for (final @NonNull Class<? extends KeyExchange> e : KeyExchange.getRegisteredKeyExchanges()) {
			this.keyExchanges.add(e);
		}
	}

	/**
	 * Returns a list of all encryption classes
	 * @return	the encryptions
	 */
	public final @NonNull List<Class<? extends Algorithm>> getAlgorithms() {
		return algorithms;
	}

	/**
	 * Returns a list of all key exchange classes
	 * @return	the key exchanges
	 */
	public final @NonNull Collection<Class<? extends KeyExchange>> getKeyExchanges() {
		return keyExchanges;
	}
	
	/**
	 * Adds a {@link StatModelListener}
	 * @param listener	the listener
	 */
	public void addListener(StatModelListener listener) {
		this.statModel.addListener(listener);
	}

	/**
	 * Adds a new {@link CryptoPass} to the {@link StatModel}
	 * @param cp	The {@link CryptoPass}
	 */
	public void addCryptoPass(CryptoPass cp) {
		this.statModel.add(cp);
	}
	
	/**
	 * @return	The {@link StatModel}
	 */
	public StatModel getStatModel() {
		return this.statModel;
	}

	/**
	 * @return	The {@link ConnectionModel}
	 */
	public ConnectionModel getConnectionModel() {
		return this.connectionModel;
	}
	
	/**
	 * @return	the current software version or <code>null</code>
	 */
	public final @Nullable String getVersion() {
		return this.version;
	}
	
	/**
	 * Sets the software version
	 * @param version	The version or <code>null</code>
	 */
	public void setVersion(final @Nullable String version) {
		this.version = version;
	}
}
