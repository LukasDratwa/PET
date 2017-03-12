package confcost.model.statistics;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.algorithm.Encryption;
import confcost.controller.algorithm.Signature;
import confcost.controller.ke.KeyExchange;
import confcost.model.SendMode;

/**
 * Represents a pass of a cryptographic algorithm, consiting of several iterations
 *
 */
public class CryptoPass {
	/**
	 * The {@link CryptoIteration}s
	 */
	private List<CryptoIteration> iterations;
	
	/**
	 * The {@link PassStatistics}
	 */
	private @Nullable PassStatistics statistics;
	
	/**
	 * The pass' {@link SendMode}
	 */
	private final @NonNull SendMode mode;
	
	public InetAddress getOther() {
		return other;
	}

	private final @NonNull InetAddress other;
	
	/**
	 * Creates a new {@link CryptoPass}.
	 * @param mode	The {@link SendMode}
	 * @param other	The communications partner
	 */
	public CryptoPass(final @NonNull SendMode mode, final @NonNull InetAddress other) {
		this.iterations = new LinkedList<>();
		
		this.mode = mode;
		this.other = other;
	}
	
	public void add(CryptoIteration ci) {
		iterations.add(ci);
	}
	
	public String toString() {
		StringBuilder ret = new StringBuilder();
		if (mode.signature != null) {
			ret.append(Signature.getName(mode.signature) + " with ");
		} 
		
		ret.append(Encryption.getName(mode.encryption));
		
		if (mode.keyExchange != null) {
			ret.append("/"+KeyExchange.getName(mode.keyExchange));
		}
		
		ret.append(" *"+mode.iterations);
		
		return ret.toString();
	}
	
	public final @NonNull Class<? extends Encryption> getAlgorithm() {
		return this.mode.encryption;
	}
	
	public final @NonNull Class<? extends KeyExchange> getKeyExchange() {
		return this.mode.keyExchange;
	}
	
	public List<CryptoIteration> getIterations() {
		return this.iterations;
	}
	
	public int getNumIterations() {
		return this.mode.iterations;
	}

	public CryptoIteration createIteration(int i) {
		final @NonNull CryptoIteration iteration = new CryptoIteration(i, this);
		iterations.add(iteration);
		return iteration;
	}
	
	public final @NonNull SendMode getSendMode() {
		return this.mode;
	}
	
	public final @NonNull PassStatistics getStatistics() {
		return this.statistics;
	}
	
	public void setStatistics(final @NonNull PassStatistics statistics) {
		this.statistics = statistics;
	}
	
	/**
	 * Returns an {@link IterationStatistics} containing the average of all iterations' values.
	 * @return	The average
	 */
	public IterationStatistics getAverage() {
		long initTime = 0;
		long remoteInitTime = 0;
		long runTime = 0;
		long remoteRunTime = 0;

		CryptoIteration[] iterations = (CryptoIteration[]) this.iterations.toArray();
		int size = iterations.length;
		for(int i = 0; i < size; i++) {
			initTime += iterations[i].getStatistics().getInitTime() / size;
			remoteInitTime += iterations[i].getStatistics().getRemoteInitTime() / size;
			runTime += iterations[i].getStatistics().getRunTime() / size;
			remoteRunTime += iterations[i].getStatistics().getRemoteRunTime() / size;
		}
		
		StatisticsNameSet nameSet = (mode.signature==null)
				?(new EncryptionStatisticsNameSet()):(new SignatureIterationNameSet());
		IterationStatistics avg = new IterationStatistics(nameSet);
		avg.setInitTime(initTime);
		avg.setRemoteInitTime(remoteInitTime);
		avg.setRunTime(runTime);
		avg.setRemoteRunTime(remoteRunTime);
		return avg;
	}
	
}