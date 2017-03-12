package confcost.controller.receive;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.Model;

/**
 * This {@link Thread} receives incoming connections.
 * 
 * @author Marc Eichler
 *
 */
public class ReceiveThread extends Thread {
	/**
	 * The {@link ServerSocket} to accept incoming connections
	 */
	private final @NonNull ServerSocket serverSocket;
	
	/**
	 * The main {@link Model}
	 */
	private final @NonNull Model model;

	/**
	 * Creates a new ReceiveThread
	 * 
	 * @param model	The main {@link Model}
	 * @param port	The port to listen on
	 * @throws IOException 	If an IO error occured
	 */
	public ReceiveThread(@NonNull Model model, @NonNull int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.model = model;
	}
	
	public void run() {
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Incoming: "+socket.getInetAddress());
				
				new HandlerThread(socket, this.model).start();
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
