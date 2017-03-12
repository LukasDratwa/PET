package confcost.model.statistics;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Statistics for a {@link CryptoPass}
 * @author Marc Eichler
 *
 */
public class PassStatistics {
	/**
	 * The time unit of the values
	 */
	public static final @NonNull String UNIT = "ns";

	/**
	 * The {@link StatisticsNameSet}
	 */
	private final @NonNull StatisticsNameSet nameSet;
	
	/**
	 * Time to initialize the cryptographic algorithm in ns
	 */
	private long initTime = -1;
	
	/**
	 * Time to initialize the cryptographic algorithm on the remote end in ns
	 */
	private long remoteInitTime = -1;

	private @Nullable SummaryStatistics sumInitTime = null;
	private @Nullable SummaryStatistics sumRemoteInitTime = null;
	private @Nullable SummaryStatistics sumRunTime = null;
	private @Nullable SummaryStatistics sumRemoteRunTime = null;
	
	/**
	 * Creates a new {@link IterationStatistics} utilizing the specified {@link StatisticsNameSet}
	 * @param nameSet	The {@link StatisticsNameSet}
	 */
	public PassStatistics(final @NonNull StatisticsNameSet nameSet) {
		this.nameSet = nameSet;
	}
	
	/**
	 * @return the initialization time in ns
	 */
	public long getInitTime() {
		return initTime;
	}
	
	/**
	 * Sets the initialization time
	 * @param initTime	The time in ns
	 */
	public void setInitTime(long initTime) {
		this.initTime = initTime;
	}
	
	/**
	 * @return	the remote init time in ns
	 */
	public long getRemoteInitTime() {
		return remoteInitTime;
	}
	
	/**
	 * Sets the remote initialization time (e.g. time for remote key generation)
	 * @param remoteInitTime	The time in ns
	 */
	public void setRemoteInitTime(long remoteInitTime) {
		this.remoteInitTime = remoteInitTime;
	}
	
	/**
	 * @return the {@link StatisticsNameSet}
	 */
	public final @NonNull StatisticsNameSet getNames() {
		return nameSet;
	}
	
	/**
	 * Aggregates the specified {@link IterationStatistics} to the overall summary.
	 * @param iteration	The {@link IterationStatistics}
	 */
	public final void aggregate(final @NonNull IterationStatistics iteration) {
		if (iteration.getInitTime() >= 0) {
			if (sumInitTime == null) sumInitTime = new SummaryStatistics();
			sumInitTime.addValue(iteration.getInitTime());
		}

		if (iteration.getRemoteInitTime() >= 0) {
			if (sumRemoteInitTime == null) sumRemoteInitTime = new SummaryStatistics();
			sumRemoteInitTime.addValue(iteration.getRemoteInitTime());
		}

		if (iteration.getRunTime() >= 0) {
			if (sumRunTime == null) sumRunTime = new SummaryStatistics();
			sumRunTime.addValue(iteration.getRunTime());
		}

		if (iteration.getRemoteRunTime() >= 0) {
			if (sumRemoteRunTime == null) sumRemoteRunTime = new SummaryStatistics();
			sumRemoteRunTime.addValue(iteration.getRemoteRunTime());
		}
	}

	public final @Nullable SummaryStatistics getSumInitTime() {
		return sumInitTime;
	}

	public final @Nullable SummaryStatistics getSumRemoteInitTime() {
		return sumRemoteInitTime;
	}

	public final @Nullable SummaryStatistics getSumRunTime() {
		return sumRunTime;
	}

	public final @Nullable SummaryStatistics getSumRemoteRunTime() {
		return sumRemoteRunTime;
	}
}
