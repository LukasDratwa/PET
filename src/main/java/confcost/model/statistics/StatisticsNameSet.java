package confcost.model.statistics;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Superclass representing name sets for statistical variables. 
 * @author Marc Eichler
 *
 */
public abstract class StatisticsNameSet {
	public abstract @NonNull String getInitTimeName();
	public abstract @NonNull String getRemoteInitTimeName();
	public abstract @NonNull String getRunTimeName();
	public abstract @NonNull String getRemoteRunTimeName();
}
