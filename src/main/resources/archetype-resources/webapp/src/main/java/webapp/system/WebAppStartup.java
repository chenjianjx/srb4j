//
//  ========================================================================
//  Copyright (c) 1995-2016 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  ========================================================================
//

package com.github.chenjianjx.srb4jfullsample.webapp.system;

import com.github.chenjianjx.srb4jfullsample.datamigration.MigrationRunner;
import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoPortalApplication;
import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoSessionFilter;
import com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support.FoRestApplication;
import com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support.FoSwaggerJaxrsConfig;
import com.github.chenjianjx.srb4jfullsample.webapp.infrahelper.rest.spring.ExitOnInitializationErrorContextLoaderListener;
import com.github.chenjianjx.srb4jfullsample.webapp.root.FoRestDocServlet;
import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.DispatcherType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.Properties;


/**
 * Example of using JSP's with embedded jetty and using a
 * lighter-weight ServletContextHandler instead of a WebAppContext.
 */
public class WebAppStartup {

    private static final Logger logger = LoggerFactory.getLogger(WebAppStartup.class);
    private static final String WEBROOT_INDEX = "/webroot/";

    public static final String BO_PORTAL_MAPPING_URL = "/bo/portal/*";
    private static StartupConfig startupConfig;
    private static AppPropertiesFactory appPropertiesFactory = new AppPropertiesFactory();


    private static Server server;


    public static void main(String[] args) throws Exception {

        loadStartupConfig();

        //run migration first
        if (startupConfig.dataMigrationOnStartup) {
            new MigrationRunner().run(startupConfig.jdbcUrl, startupConfig.dbUsername, startupConfig.dbPassword);
        } else {
            logger.warn("No data migration will be run during system startup causes it's disabled in this environment");
        }

        startServer();
        waitForInterrupt();
    }

    public static void startServer() throws Exception {
        server = new Server();

        // Define ServerConnector
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(startupConfig.port);
        server.addConnector(connector);


        // Base URI for servlet context
        URI baseUri = getWebRootResourceUri();
        logger.info("Base URI: " + baseUri);

        // Create Servlet context
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setContextPath("/");
        servletContextHandler.setResourceBase(baseUri.toASCIIString());

        // Since this is a ServletContextHandler we must manually configure JSP support.
        ServletHolder holderJsp = createJspServlet(servletContextHandler);
        holderJsp.setInitOrder(0);
        servletContextHandler.addServlet(holderJsp, "*.jsp");


        //add the spring listener
        servletContextHandler.addEventListener(createSpringContextListener(servletContextHandler));

        // fo
        servletContextHandler.addServlet(createFoRestServlet(), "/fo/rest/*");

        //swagger
        ServletHolder foRestSwaggerInitServlet = createFoRestSwaggerInitServlet();
        foRestSwaggerInitServlet.setInitOrder(1);  // this servlet on works with its init() method
        servletContextHandler.addServlet(foRestSwaggerInitServlet, "/nowhere");  //it doesn't really serve any http request

        //some other servlets
        servletContextHandler.addServlet(HealthCheckServlet.class, "/health");
        servletContextHandler.addServlet(FoRestDocServlet.class, "/fo-rest-doc");

        //bo
        if (startupConfig.enableBackOfficePortal) {
            logger.warn("Bo portal site will be enabled");
            servletContextHandler.addServlet(createBoPortalServlet(), BO_PORTAL_MAPPING_URL);
            servletContextHandler.addFilter(createBoPortalSiteMeshFilter(), BO_PORTAL_MAPPING_URL,
                    EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
            servletContextHandler.addFilter(BoSessionFilter.class, BO_PORTAL_MAPPING_URL,
                    EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        } else {
            logger.warn("Bo portal site will NOT be enabled");
        }


        // Default Servlet (always last, always named "default")
        ServletHolder holderDefault = createDefaultServlet(baseUri);
        servletContextHandler.addServlet(holderDefault, "/");


        server.setHandler(servletContextHandler);

        // Start Server
        server.start();


    }

    private static ServletHolder createDefaultServlet(URI baseUri) {
        ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
        holderDefault.setInitParameter("resourceBase", baseUri.toASCIIString());
        holderDefault.setInitParameter("dirAllowed", "true");
        return holderDefault;
    }

    private static ServletHolder createJspServlet(ServletContextHandler servletContextHandler) throws IOException {
        // Establish Scratch directory for the servlet context (used by JSP compilation)
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");

        if (!scratchDir.exists()) {
            if (!scratchDir.mkdirs()) {
                throw new IOException("Unable to create scratch directory: " + scratchDir);
            }
        }
        servletContextHandler.setAttribute("javax.servlet.context.tempdir", scratchDir);

        // Set Classloader of Context to be sane (needed for JSTL)
        // JSP requires a non-System classloader, this simply wraps the
        // embedded System classloader in a way that makes it suitable
        // for JSP to use
        ClassLoader jspClassLoader = new URLClassLoader(new URL[0], WebAppStartup.class.getClassLoader());
        servletContextHandler.setClassLoader(jspClassLoader);

        // Manually call JettyJasperInitializer on context startup
        servletContextHandler.addBean(new JspStarter(servletContextHandler));

        // Create / Register JSP Servlet (must be named "jsp" per spec)
        ServletHolder holderJsp = new ServletHolder("jsp", JettyJspServlet.class);
        holderJsp.setInitParameter("logVerbosityLevel", "DEBUG");
        holderJsp.setInitParameter("fork", "false");
        holderJsp.setInitParameter("xpoweredBy", "false");
        holderJsp.setInitParameter("compilerTargetVM", "1.8");
        holderJsp.setInitParameter("compilerSourceVM", "1.8");
        holderJsp.setInitParameter("keepgenerated", "true");
        return holderJsp;
    }

    private static URI getWebRootResourceUri() throws FileNotFoundException, URISyntaxException {
        URL indexUri = WebAppStartup.class.getResource(WEBROOT_INDEX);
        if (indexUri == null) {
            throw new FileNotFoundException("Unable to find resource " + WEBROOT_INDEX);
        }
        // Points to wherever /webroot/ (the resource) is
        return indexUri.toURI();
    }

    public static void stop() throws Exception {
        server.stop();
    }

    /**
     * Cause server to keep running until it receives a Interrupt.
     * <p/>
     * Interrupt Signal, or SIGINT (Unix Signal), is typically seen as a result of a kill -TERM {pid} or Ctrl+C
     *
     * @throws InterruptedException if interrupted
     */
    public static void waitForInterrupt() throws InterruptedException {
        server.join();
    }

    private static EventListener createSpringContextListener(ServletContextHandler contextHandler) {
        contextHandler.setInitParameter("contextConfigLocation", "classpath:spring/applicationContext.xml");
        ContextLoaderListener listener = new ExitOnInitializationErrorContextLoaderListener();
        return listener;
    }

    private static ServletHolder createFoRestSwaggerInitServlet() {
        ServletHolder holder = new ServletHolder();
        holder.setServlet(new FoSwaggerJaxrsConfig());
        return holder;
    }

    private static ServletHolder createFoRestServlet() {

        ServletHolder holder = new ServletHolder();
        holder.setServlet(new org.glassfish.jersey.servlet.ServletContainer());
        holder.setInitParameter("javax.ws.rs.Application", FoRestApplication.class.getName());
        return holder;
    }


    private static ServletHolder createBoPortalServlet() {
        ServletHolder holder = new ServletHolder();
        holder.setServlet(new org.glassfish.jersey.servlet.ServletContainer());
        holder.setInitParameter("javax.ws.rs.Application", BoPortalApplication.class.getName());
        return holder;
    }

    private static FilterHolder createBoPortalSiteMeshFilter() {
        FilterHolder holder = new FilterHolder();
        holder.setFilter(new SiteMeshFilter());
        return holder;
    }



    private static StartupConfig loadStartupConfig() throws Exception {

        Properties properties = appPropertiesFactory.getProperties();

        startupConfig = new StartupConfig();

        String dbHost = (String) properties.get("dbHost");
        int dbPort = Integer.valueOf((String) properties.get("dbPort"));
        String dbSchema = (String) properties.get("dbSchema");
        startupConfig.jdbcUrl = String.format("jdbc:mysql://%s:%s/%s", dbHost, dbPort, dbSchema);

        startupConfig.dbUsername = (String) properties.get("dbUsername");
        startupConfig.dbPassword = (String) properties.get("dbPassword");

        startupConfig.port = Integer.parseInt((String) properties.get("port"));
        startupConfig.dataMigrationOnStartup = Boolean.valueOf((String) properties.get("dataMigrationOnStartup"));

        startupConfig.enableBackOfficePortal = Boolean.valueOf((String) properties.get("enableBackOfficePortal"));

        return startupConfig;
    }

    private static final class StartupConfig {


        public String jdbcUrl;
        public String dbUsername;
        public String dbPassword;

        public int port;

        public boolean dataMigrationOnStartup;

        public boolean enableBackOfficePortal;

    }

    /**
     * JspStarter for embedded ServletContextHandlers
     * <p/>
     * This is added as a bean that is a jetty LifeCycle on the ServletContextHandler.
     * This bean's doStart method will be called as the ServletContextHandler starts,
     * and will call the ServletContainerInitializer for the jsp engine.
     */
    private static class JspStarter extends AbstractLifeCycle implements ServletContextHandler.ServletContainerInitializerCaller {
        JettyJasperInitializer sci;
        ServletContextHandler context;

        public JspStarter(ServletContextHandler context) {
            this.sci = new JettyJasperInitializer();
            this.context = context;
            this.context.setAttribute("org.apache.tomcat.JarScanner", new StandardJarScanner());
        }

        @Override
        protected void doStart() throws Exception {
            ClassLoader old = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(context.getClassLoader());
            try {
                sci.onStartup(null, context.getServletContext());
                super.doStart();
            } finally {
                Thread.currentThread().setContextClassLoader(old);
            }
        }
    }

}
