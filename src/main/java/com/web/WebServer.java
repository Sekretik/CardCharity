package com.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.io.IOException;

public class WebServer {
    Server server;

     WebServer(String ip, int port, ContextHandlerCollection contextCollection) {
        server = new Server();

        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName("server");

        HttpConfiguration http11Config = new HttpConfiguration();
        http11Config.setSendServerVersion(false);
        HttpConnectionFactory http11 = new HttpConnectionFactory(http11Config);

        ServerConnector http11Connector = new ServerConnector(server, 1, 1, http11);
        http11Connector.setHost(ip);
        http11Connector.setPort(port);

        server.setHandler(contextCollection);
        server.addConnector(http11Connector);
    }

    void startServer() {
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void stopServer() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
