package no.anderska.wta;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ShutdownHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebServer {

	private final Integer port;

    public WebServer(Integer port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
		new WebServer(getPort(8081)).start();
	}

    private void start() throws Exception {
        Server server = new Server(port);
        HandlerList handlerList = new HandlerList();
		handlerList.addHandler(new ShutdownHandler("sndglskdngsolnlan", false, true));
		handlerList.addHandler(new WebAppContext("src/main/webapp", "/"));
        server.setHandler(handlerList);
		server.start();
		System.out.println(server.getURI());
    }

    private static Integer getPort(int defaultPort) {
        String envPort = System.getenv("PORT");
        return Integer.valueOf(envPort == null ? String.valueOf(defaultPort) : envPort);
    }
}
