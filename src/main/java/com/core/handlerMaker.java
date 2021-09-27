package com.core;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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
        String originalURI = request.getOriginalURI();

        switch (request.getMethod()) {
            //region GET
            case "GET":
                response.setStatus(200);
                if(originalURI.startsWith("/owner/")) {
                    String ownerPassportNum = originalURI.substring("/owner/".length());
                    if(ownerPassportNum.isEmpty()) {
                        response.setStatus(400);
                        return;
                    }
                    try {
                        String owner = Core.db.getOwnerWithPassNumber(ownerPassportNum);
                        if(owner.equals("[]")) {
                            response.setStatus(404);
                            return;
                        }
                        response.getWriter().print(owner);
                    } catch (Exception e) {
                        response.setStatus(500);
                        return;
                    }
                }
                else if(originalURI.endsWith("/owner")) {
                    try {
                        String allOwners = Core.db.getAll("owners");
                        response.getWriter().print(allOwners);
                    } catch (Exception e) {
                        response.setStatus(500);
                        return;
                    }
                }
                else if(originalURI.startsWith("/owner?")) {
                    String ownerName = request.getParameter("name");
                    String ownerSurname = request.getParameter("surname");
                    String ownerPatronymic = request.getParameter("patronymic");
                    if(ownerName == null || ownerPatronymic == null || ownerSurname == null) {
                        response.setStatus(400);
                        return;
                    }
                    try {
                        String ownerList = Core.db.getOwnerWithFIO(ownerName, ownerSurname, ownerPatronymic);
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
                if(!originalURI.equals("/owner")) {
                    response.setStatus(400);
                    return;
                }
                JSONObject newOwner = (JSONObject) JSONValue.parse(request.getReader().readLine());
                try {
                    String ownerPassportNumber = newOwner.get("passport_number").toString();
                    String ownerName = newOwner.get("name").toString();
                    String ownerSurname = newOwner.get("surname").toString();
                    String ownerPatronymic = newOwner.get("patronymic").toString();

                    if(!Core.db.getOwnerWithPassNumber(ownerPassportNumber).equals("[]")) {
                        response.setStatus(409);
                        return;
                    }

                    Core.db.addOwner(ownerName, ownerSurname, ownerPatronymic, ownerPassportNumber);
                } catch (Exception e) {
                    if(Exception.class.equals(NullPointerException.class)) {
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

class cardHandler extends AbstractHandler {

    @Override
    public void handle(String uri, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
        request.setHandled(true);
        response.setContentType("application/json; charset=UTF-8");
        String originalURI = request.getOriginalURI();

        switch (request.getMethod()) {
            //region GET
            case "GET":
                response.setStatus(200);
                if(originalURI.startsWith("/card/")) {
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
                        if(Exception.class.equals(NumberFormatException.class)) {
                            response.setStatus(400);
                        } else {
                            response.setStatus(500);
                        }
                        return;
                    }
                }
                else if(originalURI.endsWith("/card")) {
                    try {
                        String allCards = Core.db.getAll("cards");
                        response.getWriter().print(allCards);
                    } catch (Exception e) {
                        response.setStatus(500);
                        return;
                    }
                }
                else if(originalURI.startsWith("/card?")) {
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
                            if (Exception.class.equals(NumberFormatException.class)) {
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
                            if(Exception.class.equals(NumberFormatException.class)) {
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
                response.setStatus(201);
                if(!originalURI.equals("/card")) {
                    response.setStatus(400);
                    return;
                }
                JSONObject newCard = (JSONObject) JSONValue.parse(request.getReader().readLine());
                try {
                    int cardID = Integer.parseInt(newCard.get("number").toString());
                    String cardOwner = newCard.get("owner").toString();
                    int shopID = Integer.parseInt(newCard.get("shop").toString());

                    if(!Core.db.getCardWithId(cardID).equals("[]") ||
                            Core.db.getShopWithId(shopID).equals("[]") || Core.db.getOwnerWithPassNumber(cardOwner).equals("[]")) {
                        response.setStatus(409);
                        return;
                    }

                } catch (Exception e) {
                    if(Exception.class.equals(NullPointerException.class) || Exception.class.equals(NumberFormatException.class)) {
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
                    int cardID = Integer.parseInt(originalURI.substring("/owner/".length()));
                    String card = Core.db.getCardWithId(cardID);
                    if(card.equals("[]")) {
                        response.setStatus(404);
                        return;
                    }
                    Core.db.deleteCard(cardID);
                } catch (Exception e) {
                    if(Exception.class.equals(NumberFormatException.class)) {
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

class shopHandler extends AbstractHandler {

    @Override
    public void handle(String uri, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
        request.setHandled(true);
        response.setContentType("application/json; charset=UTF-8");
        String originalURI = request.getOriginalURI();

        switch (request.getMethod()) {
            case "GET":
                response.setStatus(200);
                if(originalURI.startsWith("/shop/")) {
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
                        if(Exception.class.equals(NumberFormatException.class)) {
                            response.setStatus(400);
                            return;
                        }
                        response.setStatus(500);
                        return;
                    }
                }
                else if(originalURI.endsWith("/shop")) {
                    try {
                        String allOwners = Core.db.getAll("shops");
                        response.getWriter().print(allOwners);
                    } catch (Exception e) {
                        response.setStatus(500);
                        return;
                    }
                }
                else if(originalURI.startsWith("/shop?")) {
                    String shopName = request.getParameter("name");
                    if(shopName == null) {
                        response.setStatus(400);
                        return;
                    }
                    try {
                        String shopList = Core.db.getShopWithName(shopName);
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
