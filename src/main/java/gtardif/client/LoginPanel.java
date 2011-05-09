package gtardif.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

	public LoginPanel() {

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
			public void onClick(ClickEvent arg0) {
				label.setText("logged in : " + input.getText());
			}
		});
	}
}