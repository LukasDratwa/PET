package confcost.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A data frame that can be sent through a socket.
 * 
 * @author Marc Eichler
 *
 */
public class Frame {
	private static final String STRING_ENCODING = "UTF-8";
	
	public final @Nullable byte[] data;
	
	public Frame(@Nullable byte[] data) {
		this.data = data;
	}
	
	public Frame(@Nullable String data) throws UnsupportedEncodingException {
		if (data == null)
			this.data = null;
		else
			this.data = data.getBytes(STRING_ENCODING);
	}
	
	/**
	 * Reads data from the {@link Socket} and returns the read {@link Frame}.
	 * 
	 * @param socket	the {@link Socket}
	 * @return	The {@link Frame}
	 * @throws IOException	if an IO error occured
	 */
	public static Frame get(@NonNull Socket socket) throws IOException {
		DataInputStream in = new DataInputStream(socket.getInputStream());
		
		int length = in.readInt();
		
		byte[] data = new byte[length];
		for (int i = 0; i < length; i++) {
			data[i] = in.readByte();
		}
		
		return new Frame(data);
	}
	
	/**
	 * Writes the {@link Frame} to the specified {@link Socket}.
	 * 
	 * @param socket	The {@link Socket}
	 * @throws IOException	if an IO error occured
	 */
	public void write(@NonNull Socket socket) throws IOException {
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
		if (data == null)
			out.writeInt(0);
		else {
			out.writeInt(data.length);
			
			for (byte b : data) out.writeByte(b);
		}	
	}
	
	@Override
	public String toString() {
		try {
			return new String(this.data, STRING_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
