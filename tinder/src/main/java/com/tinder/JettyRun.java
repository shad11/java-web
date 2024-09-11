package com.tinder;

import org.eclipse.jetty.rewrite.handler.RedirectPatternRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.tinder.controller.UserServlet;

public class JettyRun {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        // Create a ResourceHandler to serve static files (CSS, etc.)
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("src/main/resources/static");
        // resourceHandler.setDirectoriesListed(false);

        // ContextHandler for static content
        ContextHandler staticContext = new ContextHandler("/");
        staticContext.setHandler(resourceHandler);

        // ContextHandler for dynamic content (servlets)
        ServletContextHandler dynamicHandler = new ServletContextHandler();
        dynamicHandler.setContextPath("/");

        dynamicHandler.addServlet(new ServletHolder(new UserServlet()), "/login");
        dynamicHandler.addServlet(new ServletHolder(new UserServlet()), "/register");
        dynamicHandler.addServlet(new ServletHolder(new UserServlet()), "/users");

        // RewriteHandler to redirect root to /users
        // If not redirected, the default behavior is to serve static content by showing the directory listing or 403 if listing is disabled
        RewriteHandler rewriteHandler = new RewriteHandler();
        rewriteHandler.setRewriteRequestURI(true);
        rewriteHandler.setRewritePathInfo(false);

        RedirectPatternRule redirectRule = new RedirectPatternRule();
                
        redirectRule.setPattern("");
        redirectRule.setLocation("/users");
        
        rewriteHandler.addRule(redirectRule);

        // Combine all handlers
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { rewriteHandler, staticContext, dynamicHandler });

        server.setHandler(handlers);

        server.start();
        server.join();
    }
}