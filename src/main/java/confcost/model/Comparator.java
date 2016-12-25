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
		// c.run();
		
		// collect data and store it into the iterationArray
		CryptoIteration iteration = new CryptoIteration(System.nanoTime() - startTime, initTime, keyLength);
		iterationArray[index++] = iteration;
		
		// return the collected data of this iteration
		return iteration;
	}
}
