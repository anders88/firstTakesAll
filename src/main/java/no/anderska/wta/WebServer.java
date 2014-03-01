package no.anderska.wta;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ShutdownHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebServer {

	private final Integer port;
    private String runFromWar;

    public WebServer(Integer port) {
        this.port = port;
    }



    public static void main(String[] args) throws Exception {
        System.out.println("Starting fta on port 8081...");
        WebServer webServer = new WebServer(getPort(8081));
        if (args != null && args.length > 0) {
            webServer.runFromWar = args[0];
        }
        webServer.start();
	}

    private void start() throws Exception {
        Server server = new Server(port);

        if (runFromWar != null) {
            WebAppContext webAppContext = new WebAppContext();
            webAppContext.setContextPath("/");
            webAppContext.setWar(runFromWar);
            server.setHandler(webAppContext);
        } else {
            HandlerList handlerList = new HandlerList();
            handlerList.addHandler(new ShutdownHandler("sndglskdngsolnlan", false, true));
		    handlerList.addHandler(new WebAppContext("src/main/webapp/", "/"));
            server.setHandler(handlerList);
        }

		server.start();
		System.out.println(server.getURI());
    }

    private static Integer getPort(int defaultPort) {
        String envPort = System.getenv("PORT");
        return Integer.valueOf(envPort == null ? String.valueOf(defaultPort) : envPort);
    }
}
