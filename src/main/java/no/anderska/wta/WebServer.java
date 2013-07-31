package no.anderska.wta;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebServer {
	
	public static void main(String[] args) throws Exception {
		String portString = System.getenv("PORT");
		Server server = new Server(Integer.valueOf(portString == null ? "8081" : portString));
		server.setHandler(new WebAppContext("src/main/webapp", "/"));
		server.start();
		if (portString == null) {
			System.out.println("http://localhost:8081/");
		}
	}
}
