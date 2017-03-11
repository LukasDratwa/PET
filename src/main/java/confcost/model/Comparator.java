package confcost.model;

import org.eclipse.jdt.annotation.NonNull;

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
	
	public CryptoIteration calculateAverage(CryptoPass pass) {
		long initTime = 0;
		long remoteInitTime = 0;
		long encryptTime = 0;
		long decryptTime = 0;

		CryptoIteration[] iterations = (CryptoIteration[]) pass.getIterations().toArray();
		int size = iterations.length;
		for(int i = 0; i < size; i++) {
			initTime += iterations[i].getInitTime() / size;
			remoteInitTime += iterations[i].getRemoteInitTime() / size;
			encryptTime += iterations[i].getEncryptionTime() / size;
			decryptTime += iterations[i].getDecryptionTime() / size;
		}
		
		CryptoIteration avg = new CryptoIteration(-1, pass);
		avg.setInitTime(initTime);
		avg.setRemoteInitTime(remoteInitTime);
		avg.setEncryptionTime(encryptTime);
		avg.setDecryptionTime(decryptTime);
		return avg;
	}
	
	public String compare(final @NonNull CryptoPass pass1, final @NonNull CryptoPass pass2) {
		String comparison = "";
		final @NonNull CryptoIteration avg1 = calculateAverage(pass1);
		final @NonNull CryptoIteration avg2 = calculateAverage(pass2);

		final @NonNull SendMode mode1 = pass1.getSendMode();
		final @NonNull SendMode mode2 = pass2.getSendMode();
		
		double percentage;
		
		percentage = avg1.getInitTime() / avg2.getInitTime();
		comparison += "initialization time: " + mode1.encryption.getName() + " " + percentage + " times as long as " + mode2.encryption.getName() + "\n";
		
		percentage = avg1.getEncryptionTime() / avg2.getEncryptionTime();
		comparison += "encryption time: " + mode1.encryption.getName() + " " + percentage + " times as long as " + mode2.encryption.getName() + "\n";
		
		percentage = avg1.getDecryptionTime() / avg2.getDecryptionTime();
		comparison += "decryption time: " + mode1.encryption.getName() + " " + percentage + " times as long as " + mode2.encryption.getName() + "\n";
		
		percentage = mode1.keyLength / mode2.keyLength;
		comparison += "key length: " + mode1.encryption.getName() + " " + percentage + " times as long as " + mode2.encryption.getName();
		
		percentage = mode1.messageLength / mode2.messageLength;
		comparison += "message length: " + mode1.encryption.getName() + " " + percentage + " times as long as " + mode2.encryption.getName();
		
		return comparison;
	}
		
}
