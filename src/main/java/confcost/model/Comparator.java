package confcost.model;

public class Comparator {
	
	private CryptoIteration[] iterationArray;
	private int index = 0;
	
	public Comparator(int noIterations) {
		iterationArray = new CryptoIteration[noIterations];
	}
	
	// returns array of all collected data up to this point
	public CryptoIteration[] getIterationArray() {
		return iterationArray;
	}
	
	// collects information about an algorithm iteration
	public CryptoIteration collectIterationData(/* CryptoAlgo c, int keyLength */) {
		// TODO get keyLength from algorithm
		int keyLength = 0;
		
		long startTime = System.nanoTime();
		
		// TODO integrate algorithm
		// c.init();
		
		long initTime = System.nanoTime() - startTime;
		startTime = System.nanoTime();
		
		// TODO integrate algorithm
		// c.encrypt();
		
		long encryptTime = System.nanoTime() - startTime;
		startTime = System.nanoTime();
		
		// TODO integrate algorithm
		// c.decrypt();
		
		long decryptTime = System.nanoTime() - startTime;
		
		// collect data and store it into the iterationArray
		CryptoIteration iteration = new CryptoIteration(encryptTime, decryptTime, initTime, keyLength);
		iterationArray[index++] = iteration;
		
		// return the collected data of this iteration
		return iteration;
	}
	
	public CryptoIteration calculateAverage() {
		long encryptTime = 0;
		long decryptTime = 0;
		long initTime = 0;
		int keyLength = 0;
		
		int arrLength = iterationArray.length;
		
		for(int i = 0; i < arrLength; i++) {
			encryptTime += iterationArray[i].getEncryptionTime();
			decryptTime += iterationArray[i].getDecryptionTime();
			initTime += iterationArray[i].getInitializationTime();
			keyLength += iterationArray[i].getKeyLength();
		}
		
		return new CryptoIteration(encryptTime, decryptTime, initTime, keyLength);
	}	
		
}
