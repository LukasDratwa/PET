package confcost.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.Model;
import confcost.view.AlgorithmConfiguration;
import confcost.view.AlgorithmConfigurationAES;
import confcost.view.AlgorithmConfigurationRSA;
import confcost.view.MainFrame;
import confcost.view.TabSend;

/**
 * Main Controller class
 * 
 * @author Marc Eichler
 *
 */
public class Controller implements ViewListener, SendButtonListener {
	private static final String HOST = "localhost";
	private static final int PORT = 1111;
	
	private final @NonNull Model model;
	private final @NonNull MainFrame view;
	
	private final ReceiveThread receiveThread;
	
	private final SendController sendController;
	
	public Controller(@NonNull Model model, @NonNull MainFrame view) throws IOException {
		this.model = model;
		this.view = view;
		this.sendController = new SendController(model);
		
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

	public void sendButtonClicked(AlgorithmConfiguration ac) {
		int keyLength = 1024;
		int msgLength = 117;
		String host = HOST;
		int port = PORT;
		int iterations = 1;
		
		System.out.println("\n");
		
		// RSA
		if(ac.getClass().equals(AlgorithmConfigurationRSA.class)) {
			System.out.println("RSA");
			AlgorithmConfigurationRSA acRSA = (AlgorithmConfigurationRSA) ac;
			
			keyLength = (Integer) acRSA.getComboBoxKeyLength().getModel().getSelectedItem();
			
			if(!acRSA.getTextFieldWestMsglength().getText().equals("")) {
				msgLength = Integer.parseInt(acRSA.getTextFieldWestMsglength().getText());
			}
			
			if(!acRSA.getTabSend().getTextFieldHost().getText().equals("")) {
				host = acRSA.getTabSend().getTextFieldHost().getText();
			}
		}
		
		// AES
		if(ac.getClass().equals(AlgorithmConfigurationAES.class)) {
			System.out.println("AES");
			AlgorithmConfigurationAES acAES = (AlgorithmConfigurationAES) ac;
			
			keyLength = (Integer) acAES.getComboBoxKeyLength().getModel().getSelectedItem();
			
			if(!acAES.getTextFieldWestMsglength().getText().equals("")) {
				msgLength = Integer.parseInt(acAES.getTextFieldWestMsglength().getText());
			}
			
			if(!acAES.getTabSend().getTextFieldHost().getText().equals("")) {
				host = acAES.getTabSend().getTextFieldHost().getText();
			}
			
			if(!acAES.getTabSend().getTextFieldPort().getText().equals("")) {
				port = Integer.parseInt(acAES.getTabSend().getTextFieldPort().getText());
			}
			
			if(!acAES.getTabSend().getSpinnerIterations().getValue().toString().equals("")) {
				iterations = Integer.parseInt(acAES.getTabSend().getSpinnerIterations().getValue().toString());
			}
		}
		
		// General settings
		if(!ac.getTabSend().getTextFieldPort().getText().equals("")) {
			port = Integer.parseInt(ac.getTabSend().getTextFieldPort().getText());
		}
		
		if(!ac.getTabSend().getSpinnerIterations().getValue().toString().equals("")) {
			iterations = Integer.parseInt(ac.getTabSend().getSpinnerIterations().getValue().toString());
		}
		
		System.out.println("SEND: " + host + ":" + port + ", msgLength=" + msgLength + ", keyLength=" + keyLength + ", iterations: " + iterations);
		
		try {
			sendController.send(ac.getSendMode().getInstance(keyLength, msgLength), iterations, host, port);
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void notifyCryptoPassSelected(TabSend tab) {
		// TODO Auto-generated method stub
		
	}
}