package com.main;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class handlerMaker {

}

class personHandler extends AbstractHandler {

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        switch (request.getMethod()) {
            case "GET":
                int requestUriLength = request.getRequestURI().length();
                if(requestUriLength > 7) {
                    String passport = request.getRequestURI().substring(7);
                    //Calls DB and asks for person
                    //puts person in request and sends back
                }
                else {
                    request.get
                }
        }
    }
}