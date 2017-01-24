package confcost.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Main Model class
 * 
 * @author Marc Eichler
 *
 */
public class Model {
	private final @NonNull List<SendMode> modes = new LinkedList<>();

	private final @NonNull ReceiveModel recvModel = new ReceiveModel();

	private final @NonNull StatModel statModel = new StatModel();
	
	/**
	 * Create a new {@link Model}
	 */
	public Model() {
		this.modes.add(new SendMode(KEProtocol.None, CProtocol.RSA));
		this.modes.add(new SendMode(KEProtocol.DiffieHellman, CProtocol.AES));
	}
	
	public Collection<SendMode> getModes() {
		return modes;
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
}
