package gtardif.client;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Test;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class LoginPanelTest extends GwtTest {
	private LoginServiceAsync mockLoginService;

	@Test
	public void canInvokeLoginService() {
		mockLoginService = mock(LoginServiceAsync.class);
		LoginPanel loginPanel = new LoginPanel(mockLoginService);

		Button button = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "button");
		Label label = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "label");
		TextBox input = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "input");

		Assert.assertEquals("", label.getText());

		input.setText("toto");
		Browser.click(button);

		verify(mockLoginService).login(eq("toto"), refEq(new LoginPanel.LoginCallback(loginPanel, "toto")));
	}

	@Test
	public void canDisplayLoggedUser() {
		LoginPanel loginPanel = new LoginPanel(mockLoginService);

		LoginPanel.LoginCallback callback = new LoginPanel.LoginCallback(loginPanel, "toto");
		callback.onSuccess(true);

		Label label = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "label");
		Button button = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "button");
		TextBox input = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "input");
		assertThat(label.getText()).isEqualTo("Logged in : toto");
		assertThat(button.getParent()).isNull();
		assertThat(input.getParent()).isNull();
	}

	@Test
	public void canDisplayWrongLogin() {
		LoginPanel loginPanel = new LoginPanel(mockLoginService);

		LoginPanel.LoginCallback callback = new LoginPanel.LoginCallback(loginPanel, "toto");
		callback.onSuccess(false);

		Label label = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "label");
		Button button = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "button");
		TextBox input = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "input");
		assertThat(label.getText()).isEqualTo("Wrong username/password");
		assertThat(button.getParent()).isNotNull();
		assertThat(input.getParent()).isNotNull();
	}
}
