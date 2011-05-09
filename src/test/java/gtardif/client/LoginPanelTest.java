package gtardif.client;

import org.junit.Assert;
import org.junit.Test;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class LoginPanelTest extends GwtTest {
	@Test
	public void test() {
		LoginPanel loginPanel = new LoginPanel();

		Button button = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "button");
		Label label = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "label");
		TextBox input = GwtReflectionUtils.getPrivateFieldValue(loginPanel, "input");

		Assert.assertEquals("", label.getText());

		input.setText("toto");
		Browser.click(button);

		Assert.assertEquals("logged in : toto", label.getText());
	}
}
