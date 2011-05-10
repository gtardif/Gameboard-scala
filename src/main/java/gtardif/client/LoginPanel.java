package gtardif.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginPanel extends Composite {
	private final Button button = new Button();
	private final Label label = new Label();
	private final TextBox input = new TextBox();

	public LoginPanel(final LoginServiceAsync loginService) {

		button.setHTML("login");

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanel.add(input);
		vPanel.add(button);
		vPanel.add(label);

		initWidget(vPanel);

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setWidth("100%");
		dialogVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				loginService.login(input.getText(), new LoginCallback(LoginPanel.this, input.getText()));
			}
		});
	}

	public void setLoggedOn(String user) {
		label.setText("Logged in : " + user);
		button.removeFromParent();
		input.removeFromParent();
	}

	public void setLoginError() {
		label.setText("Wrong username/password");
	}

	static class LoginCallback implements AsyncCallback<Boolean> {
		private final LoginPanel loginPanel;
		private final String username;

		public LoginCallback(LoginPanel loginPanel, String username) {
			this.loginPanel = loginPanel;
			this.username = username;
		}

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Boolean loggedIn) {
			if (loggedIn) {
				loginPanel.setLoggedOn(username);
			} else {
				loginPanel.setLoginError();
			}
		}
	}
}