package com.podorozhnick.moneytracker.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.io.IOException;
import java.util.EnumSet;

public class JettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JettyServer.class);

    private static JettyServer jettyServer;

    private static final int DEFAULT_PORT = 8915;
    private static final String CONTEXT_PATH = "/";
    private static final String CONFIG_LOCATION = "com.podorozhnick.moneytracker.config";
    private static final String MAPPING_URL = "/*";

    private Server server;

    public static void main(String[] args) throws Exception {
        getInstance().startJetty(getPortFromArgs(args));
    }

    public static JettyServer getInstance() {
        if (jettyServer == null) {
            jettyServer = new JettyServer();
        }
        return jettyServer;
    }

    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.valueOf(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }
        return DEFAULT_PORT;
    }

    private void startJetty(int port) throws Exception {
        LOGGER.debug("Starting server at port {}", port);
        server = new Server(port);
        server.setHandler(getServletContextHandler(getContext()));



        server.start();
        LOGGER.info("Server started at port {}", port);
        server.join();
    }

    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        dispatcherServlet.setDispatchOptionsRequest(true);
        contextHandler.addServlet(new ServletHolder(dispatcherServlet), MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));

        return contextHandler;
    }

    private static WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        return context;
    }

    public void stopServer() {
        Thread thread =  new Thread() {
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
        LOGGER.info("SERVER STOPPED");
    }

}
