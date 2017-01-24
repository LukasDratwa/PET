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
	
	public String toString() {
		return this.algorithm+"/"+this.keyExchange+" ("+this.keyLength+","+this.messageLength+") *"+numIterations;
	}
}
