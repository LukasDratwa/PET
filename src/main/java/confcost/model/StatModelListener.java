package confcost.model;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Listener for changes to a {@link StatModel}
 *
 */
public interface StatModelListener {
	public void statModelChanged(final @NonNull StatModel model);
}
