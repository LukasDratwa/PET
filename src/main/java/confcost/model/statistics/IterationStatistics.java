package confcost.model.statistics;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Superclass for all {@link CryptoPass} statistics.
 * 
 * @author Marc Eichler
 *
 */
public class IterationStatistics {
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
	
	/**
	 * Time to encrypt or sign the message in ns
	 */
	private long runTime = -1;
	
	/**
	 * Time to decrypt the message in ns
	 */
	private long remoteRunTime = -1;

	/**
	 * Creates a new {@link IterationStatistics} utilizing the specified {@link StatisticsNameSet}
	 * @param nameSet	The {@link StatisticsNameSet}
	 */
	public IterationStatistics(final @NonNull StatisticsNameSet nameSet) {
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
	 * @return	the run time in ns
	 */
	public long getRunTime() {
		return runTime;
	}
	
	/**
	 * Sets the run time (e.g. time for encryption)
	 * @param runTime	The time in ns
	 */
	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}
	
	/**
	 * @return	the remote run time in ns
	 */
	public long getRemoteRunTime() {
		return remoteRunTime;
	}
	
	/**
	 * Sets the remote run time (e.g. time for decrpytion)
	 * @param remoteRunTime	The time in ns
	 */
	public void setRemoteRunTime(long remoteRunTime) {
		this.remoteRunTime = remoteRunTime;
	}
	
	/**
	 * @return the {@link StatisticsNameSet}
	 */
	public final @NonNull StatisticsNameSet getNames() {
		return nameSet;
	}
}
