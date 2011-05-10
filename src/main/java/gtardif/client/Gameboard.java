package gtardif.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class Gameboard implements EntryPoint {
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " + "connection and try again.";

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	private final Messages messages = GWT.create(Messages.class);

	public void onModuleLoad() {
		LoginPanel loginPanel = new LoginPanel(loginService);
		RootPanel.get().add(loginPanel);
	}
}
