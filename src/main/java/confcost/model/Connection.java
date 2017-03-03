package confcost.model;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.ConnectionListener;

/**
 * Represents the status of a connection with the same or another instance of this application.
 * 
 * @author Marc
 *
 */
public class Connection {
	/**
	 * The type of the {@link Connection}
	 * @author Marc Eichler
	 *
	 */
	public enum Type {
		/**
		 * An incoming connection
		 */
		IN,
		
		/**
		 * An outgoing connection
		 */
		OUT,
		
		/**
		 * A local connection (both IN and OUT)
		 */
		LOCAL
	}
	
	/**
	 * The status of the connection
	 * @author Marc Eichler
	 *
	 */
	public enum Status {
		SETUP,
		TRANSMITTING,
		DONE,
		ERROR
	}
	
	/**
	 * The registered {@link ConnectionListener}
	 */
	private final Set<ConnectionListener> listener;
	
	/**
	 * The connection type
	 */
	private final Type type;
	
	/**
	 * The {@link SendMode}
	 */
	private SendMode mode;
	
	/**
	 * The number of iterations to be performed
	 */
	private int iterations;
	
	/**
	 * The other end
	 */
	private final InetAddress addr;
	
	/**
	 * The current {@link Status}
	 */
	private Status status;
	
	/**
	 * If in {@link Status#TRANSMITTING}, stores the number of completed iterations
	 */
	private int completedIteration;
	
	/**
	 * If the {@link Connection} is in {@link Status#ERROR}, the causing Exception is stored here
	 */
	private @Nullable Exception error;
	
	/**
	 * Constructor.
	 * 
	 * @param type	The type
	 * @param mode	The {@link SendMode}
	 * @param iterations	The number of iterations to be performed
	 * @param addr	The other address
	 */
	public Connection(final @NonNull Type type, 
			final @NonNull SendMode mode, 
			final int iterations, 
			final @NonNull InetAddress addr) {
		
		this.type = type;
		
		this.mode = mode;
		this.iterations = iterations;
		this.addr = addr;
		
		this.status = Status.SETUP;
		
		this.completedIteration = 0;
		
		this.listener = new HashSet<>();
	}
	
	public void setStatus(Status status) {
		this.status = status;
		
		this.notifyConnectionChanged();
	}
	
	/**
	 * Sets the number of completed iterations
	 * @param iteration	Number of completed iterations
	 */
	public void setProgress(int iteration) {
		if (this.status != Status.TRANSMITTING) 
			throw new IllegalStateException("Trying to set progress, while connection "
					+ "is not in Status "+Status.TRANSMITTING);
		
		this.completedIteration = iteration;
		
		this.notifyConnectionChanged();
	}
	
	public SendMode getMode() {
		return mode;
	}

	public void setMode(SendMode mode) {
		this.mode = mode;
		
		this.notifyConnectionChanged();
	}

	public int getIterations() {
		return iterations;
	}

	public int getCurrentIteration() {
		return completedIteration;
	}

	public Type getType() {
		return type;
	}

	public InetAddress getHost() {
		return addr;
	}

	public Status getStatus() {
		return status;
	}

	public Exception getError() {
		return error;
	}

	/**
	 * In case of an error, set the causing exception
	 * @param e
	 */
	public void setError(Exception e) {
		if (this.status != Status.ERROR)
			throw new IllegalStateException("Trying to set error, while connection is not in Status "+Status.ERROR);
		
		error = e;
		
		this.notifyConnectionChanged();
	}
	
	/**
	 * Adds a {@link ConnectionListener}
	 * @param listener	The listener
	 */
	public void addConnectionListener(ConnectionListener listener) {
		this.listener.add(listener);
	}
	
	/**
	 * Notifies all {@link ConnectionListener}
	 */
	public void notifyConnectionChanged() {
		for (ConnectionListener l : listener) {
			l.notifyConnectionChanged(this);
		}
	}
}
