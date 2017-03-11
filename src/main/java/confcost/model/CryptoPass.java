package confcost.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.algorithm.Encryption;
import confcost.controller.ke.KeyExchange;

public class CryptoPass {
	private List<CryptoIteration> iterations;
	
	private CryptoPassStatistic cryptoPassStatistic = null;
	
	private final @NonNull SendMode mode;
	
	public CryptoPass(final @NonNull SendMode mode) {
		this.iterations = new LinkedList<>();
		
		this.mode = mode;
	}
	
	public void add(CryptoIteration ci) {
		iterations.add(ci);
	}
	
	public void initCryptoPassStatistic() {
		if(cryptoPassStatistic == null) {
			cryptoPassStatistic = new CryptoPassStatistic(this);
		}
	}
	
	public String toString() {
		return Encryption.getName(this.mode.encryption)+"/"+KeyExchange.getName(this.mode.keyExchange)+
				" ("+this.mode.keyLength+","+this.mode.messageLength+") *"+mode.iterations;
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
	
	/**
	 * @return the cryptoPassStatistic
	 */
	public CryptoPassStatistic getCryptoPassStatistic() {
		return cryptoPassStatistic;
	}

	/**
	 * @param cryptoPassStatistic the cryptoPassStatistic to set
	 */
	public void setCryptoPassStatistic(CryptoPassStatistic cryptoPassStatistic) {
		this.cryptoPassStatistic = cryptoPassStatistic;
	}
}