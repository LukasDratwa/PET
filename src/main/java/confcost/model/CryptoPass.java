package confcost.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public class CryptoPass {
	private List<CryptoIteration> iterations;
	
	private final @NonNull CProtocol algorithm;
	private final @NonNull KEProtocol keyExchange;
	private final int keyLength;
	private final int messageLength;
	private final int numIterations;
	private CryptoPassStatistic cryptoPassStatistic = null;
	
	public CryptoPass(CProtocol algorithm, KEProtocol keyExchange, final int keyLength, final int messageLength, int iterations) {
		this.iterations = new LinkedList<>();
		
		this.algorithm = algorithm;
		this.keyExchange = keyExchange;
		this.keyLength = keyLength;
		this.messageLength = messageLength;
		this.numIterations = iterations;
	}
	
	public void add(CryptoIteration ci) {
		iterations.add(ci);
	}
	
	public void initCryptoPassStatistic() {
		if(cryptoPassStatistic == null) {
			cryptoPassStatistic = new CryptoPassStatistic(this.iterations);
		}
	}
	
	public String toString() {
		return this.algorithm+"/"+this.keyExchange+" ("+this.keyLength+","+this.messageLength+") *"+numIterations;
	}
	
	public CProtocol getAlgorithm() {
		return this.algorithm;
	}
	
	public KEProtocol getKeyExchange() {
		return this.keyExchange;
	}
	
	public List<CryptoIteration> getIterations() {
		return this.iterations;
	}
	
	public int getNumIterations() {
		return this.numIterations;
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