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
	
	public long getAvgEncryptionTime() {
		long sum = 0;
		for(CryptoIteration ci : iterations) {
			sum += ci.getEncryptionTime();
		}
		return sum/iterations.size();
	}
	
	public CryptoIteration getMinEncryptionTime() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getEncryptionTime() < result.getEncryptionTime()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	public CryptoIteration getMaxEncryptionTime() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getEncryptionTime() > result.getEncryptionTime()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	
	public long getAvgDecryptionTime() {
		long sum = 0;
		for(CryptoIteration ci : iterations) {
			sum += ci.getDecryptionTime();
		}
		return sum/iterations.size();
	}
	
	public CryptoIteration getMinDecryptionTime() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getDecryptionTime() < result.getDecryptionTime()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	public CryptoIteration getMaxDecryptionTime() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getDecryptionTime() > result.getDecryptionTime()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	
	public long getAvgInitTime() {
		long sum = 0;
		for(CryptoIteration ci : iterations) {
			sum += ci.getInitTime();
		}
		return sum/iterations.size();
	}
	
	public CryptoIteration getMinInitTime() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getInitTime() < result.getInitTime()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	public CryptoIteration getMaxInitTime() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getInitTime() > result.getInitTime()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	
	public long getAvgRemoteInitTime() {
		long sum = 0;
		for(CryptoIteration ci : iterations) {
			sum += ci.getRemoteInitTime();
		}
		return sum/iterations.size();
	}
	
	public CryptoIteration getMinRemoteInitTime() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getRemoteInitTime() < result.getRemoteInitTime()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	public CryptoIteration getMaxRemoteInitTime() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getRemoteInitTime() > result.getRemoteInitTime()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	
	public long getAvgMessageLength() {
		long sum = 0;
		for(CryptoIteration ci : iterations) {
			sum += ci.getMessageLength();
		}
		return sum/iterations.size();
	}
	
	public CryptoIteration getMinMessageLength() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getMessageLength() < result.getMessageLength()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	public CryptoIteration getMaxMessageLength() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getMessageLength() > result.getMessageLength()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	
	public long getAvgKeyLength() {
		long sum = 0;
		for(CryptoIteration ci : iterations) {
			sum += ci.getKeyLength();
		}
		return sum/iterations.size();
	}
	
	public CryptoIteration getMinKeyLength() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getKeyLength() < result.getKeyLength()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	public CryptoIteration getMaxKeyLength() {
		CryptoIteration result = null;
		for(CryptoIteration ci : iterations) {
			if(result == null) {
				result = ci;
			} else {
				if(ci.getKeyLength() > result.getKeyLength()) {
					result = ci;
				}
			}
		}
		return result;
	}
	
	public List<CryptoIteration> getIterations() {
		return this.iterations;
	}
}