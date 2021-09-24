package com.core;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class handlerMaker {

}

class ownerHandler extends AbstractHandler {
    final Logger logger = LoggerFactory.getLogger(ownerHandler.class);

    @Override
    public void handle(String url, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
        request.setHandled(true);
        response.setContentType("application/json; charset=UTF-8");

        switch (request.getMethod()) {
            case "GET":
                if(url.contains("/owner/")) {
                    String passport = request.getRequestURI().substring(url.lastIndexOf("/"));
                    try {
                        String owner = Core.db.getOwners(passport);
                        if(owner.equals("[]")) {
                            response.setStatus(404);
                        }
                        else {
                            response.setStatus(200);
                            response.getWriter().print(owner);
                        }
                    } catch (Exception e) {
                        response.setStatus(500);
                    }
                }
                else {
                    //request.get
                }
        }
    }
}
