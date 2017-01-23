package confcost.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.Model;
import confcost.view.SendModeView;
import confcost.view.View;
import confcost.view2.AlgorithmConfiguration;
import confcost.view2.AlgorithmConfigurationRSA;
import confcost.view2.MainFrame;

/**
 * Main Controller class
 * 
 * @author Marc Eichler
 *
 */
public class Controller implements ViewListener, SendViewListener, SendButtonListener {
	private static final String HOST = "localhost";
	private static final int PORT = 1111;
	
	private final @NonNull Model model;
	private final @NonNull MainFrame view;
	
	private final ReceiveThread receiveThread;
	
	private final SendController sendController;
	
	public Controller(@NonNull Model model, @NonNull MainFrame view) throws IOException {
		this.model = model;
		this.view = view;
		this.sendController = new SendController();
		
		this.view.addSendButtonListener(this);
		
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
			sendController.send(sendModeView.getSendMode().getInstance(512, 256), HOST, PORT);
		} catch (IOException | GeneralSecurityException e) {
			System.err.println("Unable to send!");
			e.printStackTrace();
		}
	}
	
	public void sendButtonClicked(AlgorithmConfiguration ac) {
		int defaultKeyLength = 1024;
		int defualtMsgLength = 117;
		
		if(ac.getClass().equals(AlgorithmConfigurationRSA.class)) {
			System.out.println("JO");
		}
		
		try {
			sendController.send(ac.getSendMode().getInstance(1024, 117));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidKeySpecException | IOException e) {
			e.printStackTrace();
		}
	}
}