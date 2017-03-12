package confcost.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.ConnectionListener;
import confcost.controller.ConnectionModelListener;

/**
 * A model managing all {@link Connection}s.
 * 
 * The {@link ConnectionModel} will register itself to each added connection as a {@link ConnectionListener} and update
 * all {@link ConnectionListener}s registered to the model when an update occurs
 * 
 * @author Marc Eichler
 *
 */
public class ConnectionModel implements ConnectionListener {

	private @NonNull List<Connection> connections;
	
	private @NonNull Set<ConnectionModelListener> listener;
	
	public ConnectionModel() {
		connections = new LinkedList<>();
		listener = new HashSet<>();
	}
	
	public void addConnection(Connection c) {
		connections.add(c);
		c.addConnectionListener(this);
		notifyConnectionAdded(c);
	}
	
	public void addConnectionModelListener(ConnectionModelListener l) {
		listener.add(l);
	}
	
	public void notifyConnectionAdded(Connection c) {
		for (ConnectionModelListener l : listener)
			l.notifyConnectionAdded(this, c);
	}

	@Override
	public void notifyConnectionChanged(Connection connection) {
		for (ConnectionModelListener l : listener)
			l.notifyAnyConnectionChanged(this, connection);
	}
	
	public final @NonNull Collection<Connection> getConnections() {
		return connections;
	}
}
