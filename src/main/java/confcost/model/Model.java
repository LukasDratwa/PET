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
	
	/**
	 * Create a new {@link Model}
	 */
	public Model() {
		this.modes.add(new SendMode(KEProtocol.DiffieHellman, CProtocol.RSA));
	}
	
	public Collection<SendMode> getModes() {
		return modes;
	}

	/**
	 * Returns the {@link ReceiveModel}.
	 * 
	 * @return	The {@link ReceiveModel}
	 */
	public ReceiveModel getReceiveModel() {
		return recvModel;
	}
}
