package experiments.lukas;

import java.util.Random;

import javax.swing.JOptionPane;

/**
 * For the use of jasyp don't forget to install the Java Cryptography Extension (JCE).
 * http://suhothayan.blogspot.de/2012/05/how-to-install-java-cryptography.html 
 * 
 * @author Lukas Dratwa
 */
public class LukasMain {
	 static {
		 try {
			 // Load chillkat.dll in Java-bin-dir (jdk)
			 System.loadLibrary("chilkat");
		 } catch (UnsatisfiedLinkError e) {
		     System.err.println("Native code library failed to load.\n" + e);
		     System.exit(1);
		 }
	 }

	public static void main(String[] args) {
		CryptoChillkat chillkat = new CryptoChillkat("000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F", "Verschlüssel das mal");
		CryptoJasypt jasyp = new CryptoJasypt("Ein ganz geheimes Passwort", "Das wird auch verschlüsselt.");
		
		for(int i=0; i<25; i++) {
			int stringLength = getRandomInt(25000, 50000);
			String newClear = getRandomString(stringLength, true, true, true);
			
			System.out.println("### Round " + (i + 1) + " ###");
			// System.out.println(newClear);
			chillkat.encryptDecryptAndPrint(newClear);
			jasyp.encryptDecryptAndPrint(newClear);
			System.out.println();
		}
	}
	
	public static String getRandomString(int length, boolean zeichenErlaubt, boolean kleinBuchstabenErlaubt, boolean großBuchstabenErlaubt){
		String result = "";
		
		if(zeichenErlaubt == false && kleinBuchstabenErlaubt == false && großBuchstabenErlaubt == false){
			JOptionPane.showMessageDialog(null, "Da alle Argumente mit 'false' angegeben wurden,\n"
					+ "ist nicht mehr das Spektrum des zufälligen Strings kontrollierbar!", "Warnung - Zufall.getOneRandomString()", JOptionPane.WARNING_MESSAGE);
		}
		
		for(int i=0; i<length; i++){
			result += getRandomChar(zeichenErlaubt, kleinBuchstabenErlaubt, großBuchstabenErlaubt);
		}
		return result;
	}
	
	public static char getRandomChar(boolean zeichenErlaubt, boolean kleinBuchstabenErlaubt, boolean großBuchstabenErlaubt){
		/* vgl: http://www.torsten-horn.de/techdocs/ascii.htm
		 * [33, 47] && [58, 64] && [91, 96] && [123, 126] <-- Zeichen
		 * [65, 90] <-- große Buchstaben
		 * [97, 122] <-- kleine Buchstaben
		 */
		
		boolean charOK = false;
		char result ='x';
		
		while(charOK == false){
			int zufallsZahl = getRandomInt(33, 126);
			result = (char)zufallsZahl;
			
			// Prävention gegen eine Endlosschleife, wenn kein Char aufgrund der Parameter kein Char gliefert werden kann.
			if(zeichenErlaubt == false && kleinBuchstabenErlaubt == false && großBuchstabenErlaubt == false){
				JOptionPane.showMessageDialog(null, "Da alle Argumente mit 'false' angegeben wurden,\n"
						+ "ist nicht mehr das Spektrum der zufälligen Chars kontrollierbar!", "Warnung - Zufall.getOneRandomChar()", JOptionPane.WARNING_MESSAGE);
				charOK = true;
			}
			
			// Prüfen ob die Zufallszahl im ASCII-Bereich der "normalen" Zeichen liegt.
			if(zeichenErlaubt == true){
				if(zufallsZahl >= 33 && zufallsZahl <= 47)
					charOK = true;
				if(zufallsZahl >= 58 && zufallsZahl <= 64)
					charOK = true;
				if(zufallsZahl >= 91 && zufallsZahl <= 96)
					charOK = true;
				if(zufallsZahl >= 123 && zufallsZahl <= 126)
					charOK = true;
			}
			
			
			// Prüfen ob die Zufallszahl im ASCII-Bereich der kleinen Buchstaben liegt.
			if(kleinBuchstabenErlaubt == true){
				if(zufallsZahl >= 97 && zufallsZahl <= 122)
					charOK = true;
			}

			
			// Prüfen ob die Zufallszahl im ASCII-Bereich der kleinen Buchstaben liegt.
			if(großBuchstabenErlaubt == true){
				if(zufallsZahl >= 65 && zufallsZahl <= 90)
					charOK = true;
			}
			
			// Wenn alle Parameter false sind und die Methode über die getOneRandomString() aufgerufen wurde, soll ein unkontrollierbarer String 
			if(zeichenErlaubt == false && kleinBuchstabenErlaubt == false && großBuchstabenErlaubt == false){
				charOK = true;
			}
		}
		
		charOK = false;
		return result;
	}
	
	public static int getRandomInt(int minInt, int maxInt){
		int randomInt;
		Random zufall = new Random();
		
		do {
			if(minInt == 0) {
				randomInt = zufall.nextInt(maxInt);
			} else {
				randomInt = zufall.nextInt(maxInt)+1;
			}
		} while (randomInt < minInt);
		
		return randomInt;
	}
}