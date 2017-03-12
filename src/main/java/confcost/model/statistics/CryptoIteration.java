package confcost.model.statistics;


import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.algorithm.Encryption;
import confcost.controller.algorithm.Signature;
import confcost.controller.ke.KeyExchange;
import confcost.model.SendMode;

/**
 * Represents an iteration of the cryptographic algorithms in a {@link CryptoPass}.
 *
 */
public class CryptoIteration {
	/**
	 * The statistics
	 */
	@NonNull IterationStatistics statistics;

	/**
	 * The index of the iteration
	 */
	private final int iteration;

	/**
	 * The {@link CryptoPass}
	 */
	private @Nullable CryptoPass pass;
	
	/**
	 * Creates a new {@link CryptoIteration}
	 * 
	 * @param iteration	The current iteration
	 * @param pass 	The {@link CryptoPass}
	 */
	public CryptoIteration(final int iteration, final @NonNull CryptoPass pass) {
		this.iteration = iteration;
		this.pass = pass;
		this.statistics = null;
	}

	public void setStatistics(final @NonNull IterationStatistics statistics) {
		this.statistics = statistics;
		
		this.pass.getStatistics().aggregate(statistics);
	}

	public final @Nullable IterationStatistics getStatistics() {
		return this.statistics;
	}
	
	public final @NonNull CryptoPass getPass() {
		return this.pass;
	}
	
	@Override
	public @NonNull String toString() {
		final @NonNull SendMode mode = this.pass.getSendMode();
		StringBuilder ret = new StringBuilder("["+(iteration+1)+"] ");
		if (mode.signature != null) {
			ret.append(Signature.getName(mode.signature) + " with ");
		} 
		
		ret.append(Encryption.getName(mode.encryption));
		
		if (mode.keyExchange != null) {
			ret.append("/"+KeyExchange.getName(mode.keyExchange));
		}
		
		return ret.toString();
	}
}
