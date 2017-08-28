package com.podorozhnick.moneytracker.config;

import com.podorozhnick.moneytracker.server.JettyServer;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.io.IOException;
import java.util.EnumSet;

@Configuration
public class JettyConfig implements ApplicationContextAware {

    @Value("${jetty.mapping.url}")
    private String mappingUrl;

    @Value("${jetty.context.path}")
    private String contextPath;

    @Value("${jetty.rewrite.redirect.regex}")
    private String redirectRegex;

    @Value("${jetty.resource.base}")
    private String resourceBase;

    @Value("${jetty.welcome.file}")
    private String welcomeFile;

    private ApplicationContext applicationContext;

    @Bean
    public JettyServer jettyServer() throws IOException {
        return new JettyServer(server());
    }

    @Bean
    public Server server() throws IOException {
        Server server = new Server();
        server.setHandler(getHandlers());
        return server;
    }

    @Bean
    public HandlerList getHandlers() throws IOException {
        HandlerList handlers = new HandlerList();
        handlers.addHandler(getRewriteHandler());
        handlers.addHandler(getResourceHandler());
        handlers.addHandler(servletContext());
        return handlers;
    }

    @Bean
    public ResourceHandler getResourceHandler() {
        ResourceHandler webResourceHandler = new ResourceHandler();
        webResourceHandler.setDirectoriesListed(true);
        webResourceHandler.setWelcomeFiles(new String[] { welcomeFile });
        webResourceHandler.setResourceBase(resourceBase);
        return webResourceHandler;
    }

    @Bean
    public RewriteHandler getRewriteHandler() {
        RewriteHandler rewrite = new RewriteHandler();
        rewrite.setRewriteRequestURI(true);
        rewrite.setRewritePathInfo(true);
        rewrite.setOriginalPathAttribute("requestedPath");
        RewriteRegexRule redirect = new RewriteRegexRule();
        redirect.setRegex(redirectRegex);
        redirect.setHandling(false);
        redirect.setReplacement("/".concat(welcomeFile));
        redirect.setTerminating(false);
        rewrite.addRule(redirect);
        return rewrite;
    }

    @Bean
    public ServletContextHandler servletContext() throws IOException {
        ServletContextHandler handler = new ServletContextHandler();
        handler.setErrorHandler(null);
        handler.setContextPath(contextPath);
        handler.addServlet(dispatcherServlet(), mappingUrl);
        handler.addFilter(new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain")), "/*", EnumSet.allOf(DispatcherType.class));

        return handler;
    }

    @Bean
    public ServletHolder dispatcherServlet() {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.setParent(applicationContext);
        ctx.register(MvcConfiguration.class);
        DispatcherServlet servlet = new DispatcherServlet(ctx);
        servlet.setDispatchOptionsRequest(true);
        return new ServletHolder(servlet);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
