package confcost.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.encryption.Encryption;
import confcost.controller.ke.KeyExchange;

/**
 * Main Model class
 * 
 * @author Marc Eichler
 *
 */
public class Model {
	private final @NonNull List<Class<? extends Encryption>> encryptions;
	private final @NonNull List<Class<? extends KeyExchange>> keyExchanges;

	private final @NonNull ReceiveModel recvModel = new ReceiveModel();

	private final @NonNull StatModel statModel = new StatModel();
	
	private final @NonNull ConnectionModel connectionModel = new ConnectionModel();
	
	/**
	 * Create a new {@link Model}
	 */
	public Model() {
		encryptions = new LinkedList<>();
		keyExchanges = new LinkedList<>();
		
		for (final @NonNull Class<? extends Encryption> e : Encryption.getRegisteredEncryptions()) {
			this.encryptions.add(e);
		}

		for (final @NonNull Class<? extends KeyExchange> e : KeyExchange.getRegisteredKeyExchanges()) {
			this.keyExchanges.add(e);
		}
	}

	/**
	 * Returns a list of all encryption classes
	 * @return	the encryptions
	 */
	public Collection<Class<? extends Encryption>> getEncryptions() {
		return encryptions;
	}

	/**
	 * Returns a list of all key exchange classes
	 * @return	the key exchanges
	 */
	public Collection<Class<? extends KeyExchange>> getKeyExchanges() {
		return keyExchanges;
	}
	
	public void addListener(StatModelListener listener) {
		this.statModel.addListener(listener);
	}

	public void addCryptoPass(CryptoPass cp) {
		this.statModel.add(cp);
	}
	
	public StatModel getStatModel() {
		return this.statModel;
	}

	public ConnectionModel getConnectionModel() {
		return this.connectionModel;
	}
}
