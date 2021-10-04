package com.core;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class HandlerMaker {
    static HandlerCollection makeHandlerCollection() {
        HandlerCollection handlerCollection = new HandlerCollection();
        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();

        ContextHandler ownerHandler = new ContextHandler("/owner");
        ownerHandler.setHandler(new OwnerHandler());
        contextHandlerCollection.addHandler(ownerHandler);

        ContextHandler shopHandler = new ContextHandler("/shop");
        shopHandler.setHandler(new ShopHandler());
        contextHandlerCollection.addHandler(shopHandler);

        ContextHandler cardHandler = new ContextHandler("/card");
        cardHandler.setHandler(new CardHandler());
        contextHandlerCollection.addHandler(cardHandler);

        ContextHandler codeHandler = new ContextHandler("/code");
        codeHandler.setHandler(new CodeHandler());
        contextHandlerCollection.addHandler(codeHandler);

        handlerCollection.addHandler(contextHandlerCollection);
        handlerCollection.addHandler(new LoggingHandler());

        return handlerCollection;
    }
}

class OwnerHandler extends AbstractHandler {
    final Logger logger = LoggerFactory.getLogger(OwnerHandler.class);

    @Override
    public void handle(String url, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
        Logger logger = LoggerFactory.getLogger("OwnerLogger");
        request.setHandled(true);
        response.setContentType("application/json; charset=UTF-8");
        String originalURI = request.getOriginalURI();
        logger.debug("Launching owner handler on URI {}", originalURI);

        switch (request.getMethod()) {
            //region GET
            case "GET":
                response.setStatus(200);
                if(originalURI.startsWith("/owner/") && !originalURI.endsWith("/owner/") && !originalURI.startsWith("/owner/?")) {
                    String ownerPassportNum = originalURI.substring("/owner/".length());
                    logger.debug("Getting owner by passNum:{}", ownerPassportNum);
                    try {
                        String owner = Core.db.getOwnerWithPassNumber(ownerPassportNum);
                        logger.debug("Getting owner from db - owner:{}", owner);
                        if(owner.equals("[]")) {
                            logger.debug("Owner is empty - 4044");
                            response.setStatus(404);
                            return;
                        }
                        response.getWriter().print(owner);
                    } catch (Exception e) {
                        response.setStatus(500);
                        return;
                    }
                }
                else if(originalURI.endsWith("/owner/") || originalURI.endsWith("/owner")) {
                    logger.debug("Getting all owners");
                    try {
                        String allOwners = Core.db.getAll("owners");
                        response.getWriter().print(allOwners);
                    } catch (Exception e) {
                        response.setStatus(500);
                        return;
                    }
                }
                else if(originalURI.startsWith("/owner?") || originalURI.startsWith("/owner/?")) {
                    String ownerName = request.getParameter("name").toLowerCase();
                    String ownerSurname = request.getParameter("surname").toLowerCase();
                    String ownerPatronymic = request.getParameter("patronymic").toLowerCase();
                    logger.debug("Getting owners by NSP:{}, {}, {}", ownerName, ownerSurname, ownerPatronymic);
                    if(ownerName == null || ownerPatronymic == null || ownerSurname == null) {
                        response.setStatus(400);
                        return;
                    }
                    try {
                        String ownerList = Core.db.getOwnerWithFIO(ownerName, ownerSurname, ownerPatronymic);
                        logger.debug("Owner by NSP:{}", ownerList);
                        if(ownerList.equals("[]")) {
                            response.setStatus(404);
                            return;
                        }
                        response.getWriter().print(ownerList);
                    } catch (Exception e) {
                        response.setStatus(500);
                        return;
                    }
                }
                else {
                    response.setStatus(400);
                    return;
                }
                break;
                //endregion

            //region POST
            case "POST":
                response.setStatus(201);
                logger.debug("Adding owner");
                if(!originalURI.equals("/owner/")) {
                    logger.debug("Wrong uri:{} - 400", originalURI);
                    response.setStatus(400);
                    return;
                }
                logger.debug("Encoding: {}", response.getCharacterEncoding());
                JSONObject newOwner = (JSONObject) JSONValue.parse(new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8));

                try {
                    logger.debug("New owner from http:{}", newOwner);
                    String ownerPassportNumber = newOwner.get("passport_number").toString();
                    String ownerName = newOwner.get("name").toString().toLowerCase();
                    String ownerSurname = newOwner.get("surname").toString().toLowerCase();
                    String ownerPatronymic = newOwner.get("patronymic").toString().toLowerCase();

                    if(!Core.db.getOwnerWithPassNumber(ownerPassportNumber).equals("[]")) {
                        response.setStatus(409);
                        return;
                    }

                    Core.db.addOwner(ownerName, ownerSurname, ownerPatronymic, ownerPassportNumber);
                } catch (Exception e) {
                    if(e.getClass().equals(NullPointerException.class)) {
                        response.setStatus(400);
                    } else {
                        response.setStatus(500);
                    }
                    return;
                }
                break;
            //endregion

            //region DELETE
            case "DELETE":
                response.setStatus(200);
                logger.debug("Deleting owner");
                if(!originalURI.startsWith("/owner/")) {
                    response.setStatus(400);
                    return;
                }
                String ownerPassportNumber = originalURI.substring("/owner/".length());
                if(ownerPassportNumber.isEmpty()) {
                    response.setStatus(400);
                    return;
                }
                try {
                    String owner = Core.db.getOwnerWithPassNumber(ownerPassportNumber);
                    if(owner.equals("[]")) {
                        response.setStatus(404);
                        return;
                    }
                    Core.db.deleteOwner(ownerPassportNumber);
                } catch (Exception e) {
                    response.setStatus(500);
                    return;
                }
                break;
            //endregion

            default:
                response.setStatus(400);
                return;
        }
    }
}

class CardHandler extends AbstractHandler {

    @Override
    public void handle(String uri, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
        Logger logger = LoggerFactory.getLogger("CardHandlerLogger");
        request.setHandled(true);
        response.addHeader("Access-Control-Allow-Origin", "https://github.com");
        response.setContentType("application/json; charset=UTF-8");
        String originalURI = request.getOriginalURI();

        switch (request.getMethod()) {
            //region GET
            case "GET":
                response.setStatus(200);
                if(originalURI.startsWith("/card/") && !originalURI.endsWith("/card/") && !originalURI.startsWith("/card/?")) {
                    String cardNumber = originalURI.substring("/card/".length());
                    if(cardNumber.isEmpty()) {
                        response.setStatus(400);
                        return;
                    }
                    try {
                        String card = Core.db.getCardWithId(Integer.parseInt(cardNumber));
                        if(card.equals("[]")) {
                            response.setStatus(404);
                            return;
                        }
                        response.getWriter().print(card);
                    } catch (Exception e) {
                        if(e.getClass().equals(NumberFormatException.class)) {
                            response.setStatus(400);
                        } else {
                            response.setStatus(500);
                        }
                        return;
                    }
                }
                else if(originalURI.endsWith("/card/")) {
                    try {
                        String allCards = Core.db.getAll("cards");
                        response.getWriter().print(allCards);
                    } catch (Exception e) {
                        response.setStatus(500);
                        return;
                    }
                }
                else if(originalURI.startsWith("/card/?")) {
                    String owner = request.getParameter("owner");
                    String number = request.getParameter("number");
                    String shopID = request.getParameter("shop");

                    if (!(number == null || shopID == null)) {
                        try {
                            int intShopID = Integer.parseInt(shopID);
                            String card = Core.db.getCardsWithCardNumberAndShopId(number, intShopID);
                            if(card.equals("[]")) {
                                response.setStatus(404);
                                return;
                            }
                            response.getWriter().print(card);
                        } catch (Exception e) {
                            if (e.getClass().equals(NumberFormatException.class)) {
                                response.setStatus(400);
                            } else {
                                response.setStatus(500);
                            }
                            return;
                        }
                    }
                    else if(!(number == null)) {
                        try {
                            String card = Core.db.getCardsWithCardNumber(number);
                            if(card.equals("[]")) {
                                response.setStatus(404);
                                return;
                            }
                            response.getWriter().print(card);
                        } catch (Exception e) {
                            response.setStatus(500);
                            return;
                        }
                    }
                    else if(!(owner == null)) {
                        try {
                            String card = Core.db.getCardsWithOwner(owner);
                            if(card.equals("[]")) {
                                response.setStatus(404);
                                return;
                            }
                            response.getWriter().print(card);
                        } catch (Exception e) {
                            response.setStatus(500);
                            return;
                        }
                    }
                    else if(!(shopID == null)) {
                        try {
                            int intShopID = Integer.parseInt(shopID);
                            String card = Core.db.getCardsWithShopId(intShopID);
                            if(card.equals("[]")) {
                                response.setStatus(404);
                                return;
                            }
                            response.getWriter().print(card);
                        } catch (Exception e) {
                            if(e.getClass().equals(NumberFormatException.class)) {
                                response.setStatus(400);
                                return;
                            }
                            response.setStatus(500);
                            return;
                        }
                    }
                }
                break;
            //endregion

            //region POST
            case "POST":
                logger.debug("Creating new card");
                response.setStatus(201);
                if(!originalURI.equals("/card/")) {
                    response.setStatus(400);
                    return;
                }
                JSONObject newCard = (JSONObject) JSONValue.parse(new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
                try {
                    String cardNumber = newCard.get("number").toString();
                    String cardOwner = newCard.get("owner").toString();
                    int shopID = Integer.parseInt(newCard.get("shop").toString());

                    if(shopID == 1 && cardNumber.length() != 12) {
                        logger.error("Wrong card number for shop {}: {}", shopID, cardNumber);
                    }

                    logger.debug("Adding card number = {}, shopID = {}, ownerNum = {}", cardNumber, shopID, cardOwner);

                    if(!Core.db.getCardsWithCardNumberAndShopId(cardNumber, shopID).equals("[]") ||
                            Core.db.getShopWithId(shopID).equals("[]") || Core.db.getOwnerWithPassNumber(cardOwner).equals("[]")) {
                        logger.debug("Wrong data - 409");
                        response.setStatus(409);
                        return;
                    }
                    Core.db.addCard(cardNumber, cardOwner, shopID);
                    logger.debug("Card added");

                } catch (Exception e) {
                    logger.debug(e.toString());
                    if(e.getClass().equals(NullPointerException.class) || e.getClass().equals(NumberFormatException.class)) {
                        response.setStatus(400);
                    } else {
                        response.setStatus(500);
                    }
                    return;
                }
                break;
                //endregion

            //region DELETE
            case "DELETE":
                response.setStatus(200);
                if(!originalURI.startsWith("/card/")) {
                    response.setStatus(400);
                    return;
                }
                try {
                    int cardID = Integer.parseInt(originalURI.substring("/card/".length()));
                    String card = Core.db.getCardWithId(cardID);
                    if(card.equals("[]")) {
                        response.setStatus(404);
                        return;
                    }
                    Core.db.deleteCard(cardID);
                } catch (Exception e) {
                    if(e.getClass().equals(NumberFormatException.class)) {
                        response.setStatus(400);
                    } else {
                        response.setStatus(500);
                    }
                    return;
                }
                break;
                //endregion

            default:
                response.setStatus(400);
                return;
        }
    }
}

class ShopHandler extends AbstractHandler {

    @Override
    public void handle(String uri, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
        Logger logger = LoggerFactory.getLogger("ShopLogger");
        request.setHandled(true);
        response.setContentType("application/json; charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "https://github.com");
        String originalURI = request.getOriginalURI();

        switch (request.getMethod()) {
            case "GET":
                response.setStatus(200);
                if(originalURI.startsWith("/shop/") && !originalURI.endsWith("/shop/") && !originalURI.startsWith("/shop/?")) {
                    String shopID = originalURI.substring("/shop/".length());
                    if(shopID.isEmpty()) {
                        response.setStatus(400);
                        return;
                    }
                    try {
                        String shop = Core.db.getShopWithId(Integer.parseInt(shopID));
                        if(shop.equals("[]")) {
                            response.setStatus(404);
                            return;
                        }
                        response.getWriter().print(shop);
                    } catch (Exception e) {
                        if(e.getClass().equals(NumberFormatException.class)) {
                            response.setStatus(400);
                            return;
                        }
                        response.setStatus(500);
                        return;
                    }
                }
                else if(originalURI.endsWith("/shop/")) {
                    try {
                        String allOwners = Core.db.getAll("shops");
                        response.getWriter().print(allOwners);
                    } catch (Exception e) {
                        response.setStatus(500);
                        return;
                    }
                }
                else if(originalURI.startsWith("/shop/?")) {
                    String shopName = request.getParameter("name");
                    logger.debug("shopName = {}", shopName);
                    if(shopName == null) {
                        logger.debug("shop name is null");
                        response.setStatus(400);
                        return;
                    }
                    try {
                        String shopList = Core.db.getShopWithName(shopName.toLowerCase());
                        if(shopList.equals("[]")) {
                            response.setStatus(404);
                            return;
                        }
                        response.getWriter().print(shopList);
                    } catch (Exception e) {
                        response.setStatus(500);
                        return;
                    }
                }
                else {
                    response.setStatus(400);
                    return;
                }
                break;
        }
    }
}

class CodeHandler extends AbstractHandler {
    Logger codeLogger = LoggerFactory.getLogger("CodeLogger");

    @Override
    public void handle(String uri, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
        request.setHandled(true);
        response.setStatus(200);
        response.setContentType("image/png");
        response.addHeader("Access-Control-Allow-Origin", "https://github.com");
        String originalURI = request.getOriginalURI();
        codeLogger.debug("Getting code image, URI: {}", originalURI);
        try {
            int shopID = Integer.parseInt(request.getParameter("shop"));
            codeLogger.debug("ShopID: {}", shopID);
            String shop = Core.db.getShopWithId(shopID);
            LoggingHandler.logger.info(shop);
            if(shop.equals("[]")) {
                response.setStatus(404);
                return;
            }
            BufferedImage image = Core.image.getImagePath(shopID);
            ImageIO.write(image, "png", response.getOutputStream());

        } catch (Exception e) {
            if(e.getClass().equals(NumberFormatException.class)) {
                response.setStatus(400);
                return;
            }
            response.setStatus(500);
            return;
        }
    }
}

class LoggingHandler extends AbstractHandler {
    static Logger logger = LoggerFactory.getLogger(LoggingHandler.class);

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("Http request from {}: method - {}, URI - {}  Response code: {}", request.getRemoteAddr(), request.getMethod(), request.getOriginalURI(), response.getStatus());
    }
}

