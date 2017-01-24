package confcost.model;

public class Comparator {
	
		
	/*
	// collects information about an algorithm iteration
	public CryptoIteration collectIterationData( CryptoAlgo c, int keyLength, long messageLength) {
		// TODO get keyLength from algorithm
		int keyLength = 0;
		byte[] message = new byte[10];
		
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
		CryptoIteration iteration = new CryptoIteration(initTime, encryptTime, decryptTime, keyLength, message);
		iterationArray[index++] = iteration;
		
		// return the collected data of this iteration
		return iteration;
	}
	*/
	
	public CryptoIteration calculateAverage(CryptoIteration[] iterationArray) {
		long initTime = 0;
		long remoteInitTime = 0;
		long encryptTime = 0;
		long decryptTime = 0;
		int keyLength = 0;
		long messageLength = 0;
		
		int arrLength = iterationArray.length;
		for(int i = 0; i < arrLength; i++) {
			initTime += iterationArray[i].getInitTime() / arrLength;
			remoteInitTime += iterationArray[i].getRemoteInitTime() / arrLength;
			encryptTime += iterationArray[i].getEncryptionTime() / arrLength;
			decryptTime += iterationArray[i].getDecryptionTime() / arrLength;
			keyLength += iterationArray[i].getKeyLength() / arrLength;
			messageLength += iterationArray[i].getMessageLength() / arrLength;
		}
		
		CryptoIteration avg = new CryptoIteration(iterationArray[0].getAlgorithm(), iterationArray[0].getKeyExchange(), keyLength, messageLength);
		avg.setInitTime(initTime);
		avg.setRemoteInitTime(remoteInitTime);
		avg.setEncryptionTime(encryptTime);
		avg.setDecryptionTime(decryptTime);
		return avg;
	}
	
	public String compare(CryptoIteration[] arr1, CryptoIteration[] arr2) {
		String comparison = "";
		CryptoIteration priAlgoValues = calculateAverage(arr1);
		CryptoIteration secAlgoValues = calculateAverage(arr2);
		double percentage;
		
		percentage = priAlgoValues.getInitTime() / secAlgoValues.getInitTime();
		comparison += "initialization time: " + priAlgoValues.getAlgorithm().getName() + " " + percentage + " times as long as " + secAlgoValues.getAlgorithm().getName() + "\n";
		
		percentage = priAlgoValues.getEncryptionTime() / secAlgoValues.getEncryptionTime();
		comparison += "encryption time: " + priAlgoValues.getAlgorithm().getName() + " " + percentage + " times as long as " + secAlgoValues.getAlgorithm().getName() + "\n";
		
		percentage = priAlgoValues.getDecryptionTime() / secAlgoValues.getDecryptionTime();
		comparison += "decryption time: " + priAlgoValues.getAlgorithm().getName() + " " + percentage + " times as long as " + secAlgoValues.getAlgorithm().getName() + "\n";
		
		percentage = priAlgoValues.getKeyLength() / secAlgoValues.getKeyLength();
		comparison += "key length: " + priAlgoValues.getAlgorithm().getName() + " " + percentage + " times as long as " + secAlgoValues.getAlgorithm().getName();
		
		percentage = priAlgoValues.getMessageLength() / secAlgoValues.getMessageLength();
		comparison += "message length: " + priAlgoValues.getAlgorithm().getName() + " " + percentage + " times as long as " + secAlgoValues.getAlgorithm().getName();
		
		return comparison;
	}
		
}
