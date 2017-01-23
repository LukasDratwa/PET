package confcost.util;

public class HexString {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	private String string;
	
	public HexString(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    this.string = new String(hexChars);
	}
	
	@Override
	public String toString() {
		return string;
	}
}
