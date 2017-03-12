package confcost.model.statistics;

import org.eclipse.jdt.annotation.NonNull;

public class EncryptionStatisticsNameSet extends StatisticsNameSet {

	@Override
	public @NonNull String getInitTimeName() {
		return "Key generation";
	}

	@Override
	public @NonNull String getRemoteInitTimeName() {
		return "Remote Key Generation";
	}

	@Override
	public @NonNull String getRunTimeName() {
		return "Encryption Time";
	}

	@Override
	public @NonNull String getRemoteRunTimeName() {
		return "Decryption Time";
	}
}
