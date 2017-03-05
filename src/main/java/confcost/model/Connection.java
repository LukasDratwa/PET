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
 * @author Marc Eichler
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
	
	/**
	 * Sets the {@link Status}.
	 * @param status	The new status
	 */
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
	
	/**
	 * @return	the {@link SendMode}
	 */
	public SendMode getMode() {
		return mode;
	}

	/**
	 * @return the maximum number of iterations
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * @return The number of performed iterations
	 */
	public int getCurrentIteration() {
		return completedIteration;
	}

	/**
	 * @return	the {@link Type}
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the {@link InetAddress}
	 */
	public InetAddress getHost() {
		return addr;
	}

	/**
	 * @return	the current {@link Status}
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @return The error's cause or <code>null</code>
	 */
	public Exception getError() {
		return error;
	}

	/**
	 * Sets the status to {@link Status#ERROR} and sets the cause 
	 * @param cause	The cause
	 */
	public void setError(Exception cause) {
		this.status = Status.ERROR;
		
		error = cause;
		
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
