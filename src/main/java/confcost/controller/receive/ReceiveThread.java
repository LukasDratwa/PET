package confcost.controller.receive;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.Controller;

/**
 * This {@link Thread} receives incoming connections.
 * 
 * @author Marc Eichler
 *
 */
public class ReceiveThread extends Thread {
	private final @NonNull Controller controller;

	private final @NonNull ServerSocket serverSocket;

	/**
	 * Creates a new ReceiveThread
	 * 
	 * @param controller	The {@link Controller}
	 * @param port			The port to listen on
	 * @throws IOException 
	 */
	public ReceiveThread(@NonNull Controller controller, @NonNull int port) throws IOException {
		this.controller = controller;
		this.serverSocket = new ServerSocket(port);
	}
	
	public void run() {
		try {
			while (true) {
				Socket s = serverSocket.accept();
				System.out.println("Incoming: "+s.getInetAddress());
				
				new HandlerThread(s).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ReceiveThread: Done.");
	}
	
	public void interrupt() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
