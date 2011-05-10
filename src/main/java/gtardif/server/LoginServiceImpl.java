package gtardif.server;

import gtardif.client.LoginService;
import gtardif.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	@Override
	public boolean login(String username) throws IllegalArgumentException {
		if (!FieldVerifier.isValidName(username)) {
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}
		return true;
	}
}
