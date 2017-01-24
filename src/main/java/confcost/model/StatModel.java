package confcost.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;

public class StatModel {
	private final List<CryptoPass> stats;
	private final Set<StatModelListener> listener;
	
	public StatModel() {
		this.stats = new LinkedList<>();
		this.listener = new HashSet<>();
	}
	
	/**
	 * Adds a {@link CryptoIteration} and notifies all listeners.
	 * @param c	the {@link CryptoIteration}
	 */
	public void add(CryptoPass c) {
		stats.add(c);
		
		this.notifyListeners();
	}
	
	public CryptoPass[] getCryptoPasses() {
		CryptoPass[] array = new CryptoPass[stats.size()];
		
		stats.toArray(array);
		
		return array;
	}
	/**
	 * Adds a {@link StatModelListener}.
	 * @param listener	The listener
	 */
	public void addListener(final @NonNull StatModelListener listener) {
		this.listener.add(listener);
	}
	
	/**
	 * Notify all listeners that a change occured.
	 */
	public void notifyListeners() {
		for (StatModelListener l : this.listener) l.statModelChanged(this);
	}
}
