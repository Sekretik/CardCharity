package com.image;

import com.codeGenerations.CreateCode;
import com.codeGenerations.EncodingForCode;
import com.core.Core;
import com.database.DataBase;
import com.google.zxing.WriterException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class Image {

    private final Logger logger = LoggerFactory.getLogger(Image.class);

    public BufferedImage getImagePath(int shopId) throws Exception {
        JSONObject card = null;
        String cardNumber = "";
        try {
            JSONArray json = (JSONArray) JSONValue.parse(Core.db.getOwnersCardWithMinUse(shopId));
            card = (JSONObject) JSONValue.parse(json.get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cardNumber = card.get("number").toString();
        
        BufferedImage resultReturn = null;
        EncodingForCode encode;

        switch (shopId){
            case 1:
                encode = EncodingForCode.EAN_13;
                try {
                    resultReturn = CreateCode.createQR(cardNumber,encode.toString());
                    logger.trace("Card hs received: " + card.get("number").toString());
                } catch (WriterException e) {
                    logger.trace(e.toString());
                    throw new Exception("Server exception");
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.trace(e.toString());
                    throw new Exception("Server exception");
                }
                break;
            case 2:
                encode = EncodingForCode.QR_CODE;
                try {
                    resultReturn = CreateCode.createQR(cardNumber,encode.toString());
                    logger.trace("Card hs received: " + card.get("number").toString());
                } catch (WriterException e) {
                    logger.trace(e.toString());
                    throw new Exception("Server exception");
                } catch (IOException e) {
                    logger.trace(e.toString());
                    throw new Exception("Server exception");
                }
                break;
        }
        return resultReturn;
    }
}
