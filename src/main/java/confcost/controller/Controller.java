package confcost.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.receive.ReceiveThread;
import confcost.controller.send.SendController;
import confcost.model.Model;
import confcost.model.SendMode;
import confcost.view.MainFrame;
import confcost.view.send.AlgorithmConfiguration;
import confcost.view.send.GeneralSettings;
import confcost.view.send.TabSend;

/**
 * Main controller class.
 * 
 * @author Marc Eichler
 *
 */
public class Controller implements ViewListener, SendButtonListener, SettingsListener {
	/**
	 * The default network host
	 */
	private static final String DEFAULT_HOST = "localhost";
	
	/**
	 * The default port to listen on and send to
	 */
	private static final int DEFAULT_PORT = 1111;

	/**
	 * Default value for whether the key exchange should be performed every iteration
	 */
	private static final boolean DEFAULT_KEY_EXCHANGE_EVERY_ITERATION = false;

	/**
	 * Default iteration count
	 */
	private static final int DEFAULT_ITERATIONS = 100;
	
	/**
	 * The main {@link Model}
	 */
	private final @NonNull Model model;
	
	/**
	 * The {@link MainFrame}
	 */
	private final @NonNull MainFrame view;
	
	/**
	 * The {@link ReceiveThread}
	 */
	private final ReceiveThread receiveThread;
	
	/**
	 * The {@link SendController}
	 */
	private final SendController sendController;
	
	/**
	 * The currently selected host
	 */
	private @NonNull String host = DEFAULT_HOST;
	
	/**
	 * The currently selected port
	 */
	private int port = DEFAULT_PORT;
	
	/**
	 * True iff key exchange is supposed to be performed for every iteration
	 */
	private boolean keyExchangeEveryIteration = DEFAULT_KEY_EXCHANGE_EVERY_ITERATION;
	
	/**
	 * The currently selected iterations
	 */
	private int iterations = DEFAULT_ITERATIONS;
	
	/**
	 * Constructor
	 * 
	 * @param model	The main model
	 * @param view	The main view
	 * @throws IOException
	 */
	public Controller(@NonNull Model model, @NonNull MainFrame view) throws IOException {
		this.model = model;
		this.view = view;
		this.sendController = new SendController(model);

		this.view.addSendButtonListener(this);
		this.view.addSettingsListener(this);
		
		this.receiveThread = new ReceiveThread(this, DEFAULT_PORT);
	}
	
	/**
	 * Starts the {@link Controller}.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void start() throws UnknownHostException, IOException {
		view.setHost(host);
		view.setPort(port);
		view.setIterations(iterations);
		view.setKeyExchangeEveryIteration(keyExchangeEveryIteration);
		
		view.setVisible(true);
		
		receiveThread.start();

		sendController.connect();
	}
	
	/**
	 * Stops the {@link Controller}.
	 */
	public void stop() {
		receiveThread.interrupt();
	}

	@Override
	public void sendButtonClicked(final AlgorithmConfiguration ac) {
		new Thread(){
		     @Override
		     public void run(){		 		
		 		SendMode s = ac.getModeInfo();
		 		try {
		 			sendController.send(s, iterations, keyExchangeEveryIteration, host, port);
		 		} catch (GeneralSecurityException | IOException e) {
		 			e.printStackTrace();
		 		}	
		     }
		}.start();	
	}

	@Override
	public void notifyCryptoPassSelected(TabSend tab) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifySaveButtonPressed(GeneralSettings settings) {
		this.host = settings.getHost();
		this.port = settings.getPort();
		this.keyExchangeEveryIteration = settings.getKeyExchangeEveryIteration();
		this.iterations = settings.getIterations();
	}
}