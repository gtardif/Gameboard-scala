package gtardif;

import static com.google.inject.Guice.*;
import com.google.inject.AbstractModule;

public class JettyServerMain {
	public static void main(String[] args) throws Exception{
		JettyServer jetty = createInjector(new MainWebModule()).getInstance(JettyServer.class);
		jetty.initialize();
		jetty.start();
	}

	static class MainWebModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(JettyServer.class).toInstance(new JettyServer("/", 8080));
		}

		public JettyServer getNodeInstance() {
			return createInjector(this).getInstance(JettyServer.class);
		}
	}
}
