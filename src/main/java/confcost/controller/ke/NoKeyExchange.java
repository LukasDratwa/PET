package confcost.controller.ke;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Implements functionality to exchange keys if no key exchange protocol was specified.
 * 
 * <p>
 * {@link NoKeyExchange} simply sends any required keys to the communications partner unencrypted.
 * </p>
 * 
 * @author Marc Eichler
 *
 */
public class NoKeyExchange extends KeyExchange {

	@Override
	public void send(@NonNull Socket socket)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException,
			InvalidKeyException, IOException, InvalidKeySpecException, InvalidParameterSpecException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receive(@NonNull Socket socket)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException,
			InvalidKeyException, IOException, InvalidKeySpecException, InvalidParameterSpecException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setKeyLength(int bit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
