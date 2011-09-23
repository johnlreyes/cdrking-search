package per.search.server;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.xml.XmlConfiguration;

public class JettyMain {

	public static void main(String... args) throws Exception {
		Server jetty = new Server();
		String configFile = "etc/jetty.xml";
		XmlConfiguration configuration = new XmlConfiguration(new File(configFile).toURI().toURL());
		configuration.configure(jetty);
		jetty.start();
	}
}
