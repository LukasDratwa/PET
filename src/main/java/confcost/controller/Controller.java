package confcost.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.Model;
import confcost.view.SendModeView;
import confcost.view.View;
import confcost.view2.AlgorithmConfiguration;
import confcost.view2.AlgorithmConfigurationAES;
import confcost.view2.AlgorithmConfigurationECIES;
import confcost.view2.AlgorithmConfigurationRC2;
import confcost.view2.AlgorithmConfigurationRSA;
import confcost.view2.MainFrame;
import confcost.view2.TabSend;

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

	@Override
	public void notifyClosing(@NonNull View view) {
		System.out.println("Closed");
		this.stop();
		view.setVisible(false);
		view.dispose();
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
		// ECIES
		if(ac.getClass().equals(AlgorithmConfigurationECIES.class)) {
			System.out.println("ECIES");
			AlgorithmConfigurationECIES acECIES = (AlgorithmConfigurationECIES) ac;
			
			keyLength = (Integer) acECIES.getComboBoxKeyLength().getModel().getSelectedItem();
			
			if(!acECIES.getTextFieldWestMsglength().getText().equals("")) {
				msgLength = Integer.parseInt(acECIES.getTextFieldWestMsglength().getText());
			}
			
			if(!acECIES.getTabSend().getTextFieldHost().getText().equals("")) {
				host = acECIES.getTabSend().getTextFieldHost().getText();
			}
			
			if(!acECIES.getTabSend().getTextFieldPort().getText().equals("")) {
				port = Integer.parseInt(acECIES.getTabSend().getTextFieldPort().getText());
			}
			
			if(!acECIES.getTabSend().getSpinnerIterations().getValue().toString().equals("")) {
				iterations = Integer.parseInt(acECIES.getTabSend().getSpinnerIterations().getValue().toString());
			}
		}
		// RC2
		if(ac.getClass().equals(AlgorithmConfigurationRC2.class)) {
			System.out.println("RC2");
			AlgorithmConfigurationRC2 acRC2 = (AlgorithmConfigurationRC2) ac;
			
			keyLength = (Integer) acRC2.getComboBoxKeyLength().getModel().getSelectedItem();
			
			if(!acRC2.getTextFieldWestMsglength().getText().equals("")) {
				msgLength = Integer.parseInt(acRC2.getTextFieldWestMsglength().getText());
			}
			
			if(!acRC2.getTabSend().getTextFieldHost().getText().equals("")) {
				host = acRC2.getTabSend().getTextFieldHost().getText();
			}
			
			if(!acRC2.getTabSend().getTextFieldPort().getText().equals("")) {
				port = Integer.parseInt(acRC2.getTabSend().getTextFieldPort().getText());
			}
			
			if(!acRC2.getTabSend().getSpinnerIterations().getValue().toString().equals("")) {
				iterations = Integer.parseInt(acRC2.getTabSend().getSpinnerIterations().getValue().toString());
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

	@Override
	public void notifySendButtonPressed(SendModeView sendModeView) {
		// TODO Auto-generated method stub
		
	}
}