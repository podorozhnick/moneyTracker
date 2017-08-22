package com.podorozhnick.moneytracker.server;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Slf4j
public class JettyServer {

    @Autowired
    private StartSettings settings;

    private Server server;

    public JettyServer(Server server) {
        this.server = server;
    }

    @PostConstruct
    private void startJetty() throws Exception {

        ServerConnector httpConnector = new ServerConnector(server);
        httpConnector.setPort(settings.getPort());
        server.addConnector(httpConnector);

        server.start();
        log.info("Server started at port {}", settings.getPort());
//        server.join();
    }

    public void stopServer() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    server.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        log.info("SERVER STOPPED");
    }

}
