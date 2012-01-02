package gtardif;

import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler.Context;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JettyServer {
	private static final Logger log = LoggerFactory.getLogger(JettyServer.class);

	private final int port;
	private final String webContext;

	private Server server;
	private AbstractConnector httpConnector;
	private WebAppContext webAppContext;

	public JettyServer(String webContext, int port) {
		this.webContext = webContext;
		this.port = port;
	}

	public void initialize() {
		server = new Server();

		httpConnector = createHttpConnector();
		server.addConnector(httpConnector);
		webAppContext = createWebApplicationContext();
		server.setHandler(webAppContext);
	}

	public void start() throws Exception {
		log.info("Starting Jetty server (...)");
		server.start();
		log.info("Jetty server started on port " + getPort());
	}

	public void stop() throws Exception {
		server.stop();
		webAppContext.destroy();
	}

	public boolean isStarted() {
		return server.isStarted();
	}

	public int getPort() {
		return httpConnector.getLocalPort();
	}

	protected String getWebContext() {
		return webContext;
	}

	public <T> T getSpringBean(Class<T> clazz) {
		return getWebApplicationContext().getBean(clazz);
	}

	public String getSpringProperty(String propertyName) {
		return (String) getWebApplicationContext().getBean(propertyName);
	}

	private SelectChannelConnector createHttpConnector() {
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setMaxIdleTime(1000 * 60 * 60 * 24);
		connector.setPort(port);
		connector.setResolveNames(false);

		return connector;
	}

	public WebApplicationContext getWebApplicationContext() {
		Context servletContext = webAppContext.getServletContext();
		return WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}

	protected WebAppContext createWebApplicationContext() {
		String warLocation = System.getProperty("gameboard_webapp.war", "./src/main/webapp");
		log.info("Running WAR: " + warLocation);

		WebAppContext webApp = new WebAppContext(warLocation, webContext);
		webApp.setParentLoaderPriority(true);
		webApp.setMaxFormContentSize(-1);
		return webApp;
	}
}
