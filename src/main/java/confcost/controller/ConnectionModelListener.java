package confcost.controller;

import confcost.model.Connection;
import confcost.model.ConnectionModel;
/**
 * An interface of a listener for {@link ConnectionModel} changes.
 * 
 * @author Marc Eichler
 *
 */
public interface ConnectionModelListener {
	/**
	 * Callback method for when a {@link Connection} was added to a {@link ConnectionModel}.
	 * @param model	The {@link ConnectionModel}
	 * @param connection	The {@link Connection}
	 */
	public void notifyConnectionAdded(ConnectionModel model, Connection connection);
	
	/**
	 * Callback method for when one of the models' {@link Connection}s has been changed
	 * @param model	The {@link ConnectionModel}
	 * @param connection	The {@link Connection}
	 */
	public void notifyAnyConnectionChanged(ConnectionModel model, Connection connection);
}
