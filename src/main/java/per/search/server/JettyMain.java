package per.search.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyMain {

	public static void main(String... args) throws Exception {
//		Server jetty = new Server();
//		String configFile = "etc/jetty.xml";
//		XmlConfiguration configuration = new XmlConfiguration(new File(configFile).toURI().toURL());
//		configuration.configure(jetty);
//		jetty.start();
		
		String webappDirLocation = "src/main/webapp/";
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext root = new WebAppContext();
        root.setContextPath("/");
        root.setDescriptor(webappDirLocation+"/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);
        root.setParentLoaderPriority(true);
        server.setHandler(root);
        server.start();
        server.join(); 
	}
}
