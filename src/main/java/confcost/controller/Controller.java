package confcost.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.Model;
import confcost.view.SendModeView;
import confcost.view.View;

/**
 * Main Controller class
 * 
 * @author Marc Eichler
 *
 */
public class Controller implements ViewListener, SendViewListener {
	private static final String HOST = "localhost";
	private static final int PORT = 1111;
	
	private final @NonNull Model model;
	private final @NonNull View view;
	
	private final ReceiveThread receiveThread;
	
	private final SendController sendController;
	
	public Controller(@NonNull Model model, @NonNull View view) throws IOException {
		this.model = model;
		this.view = view;
		this.sendController = new SendController();
		
		this.view.addViewListener(this);
		
		this.receiveThread = new ReceiveThread(this, PORT);
	}
	
	public void start() throws UnknownHostException, IOException {
		view.setVisible(true);
		
		receiveThread.start();
		sendController.connect();
	}
	
	public void stop() {
		receiveThread.interrupt();
	}

	@Override
	public void notifyClosing(@NonNull View view) {
		System.out.println("Closed");
		this.stop();
		view.setVisible(false);
		view.dispose();
	}

	@Override
	public void notifySendButtonPressed(SendModeView sendModeView) {
		try {
			sendController.send(sendModeView.getSendMode().getInstance(256, 256), HOST, PORT);
		} catch (IOException | GeneralSecurityException e) {
			System.err.println("Unable to send!");
			e.printStackTrace();
		}
	}
}
