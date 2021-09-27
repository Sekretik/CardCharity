package com.image;

import com.codeGenerations.CreateCode;
import com.codeGenerations.EncodingForCode;
import com.core.Core;
import com.google.zxing.WriterException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.nio.file.Path;

public class Image {

    public Path getImagePath(int shopId){
        JSONObject shop = null;
        JSONObject card = null;
        String cardNumber = "";
        try {
            JSONArray json = (JSONArray) JSONValue.parse(Core.db.getShopWithId(shopId));
            shop = (JSONObject) JSONValue.parse(json.get(0).toString());
            json = (JSONArray) JSONValue.parse(Core.db.getOwnersCardWithMinUse(shopId));
            card = (JSONObject) JSONValue.parse(json.get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cardNumber = card.get("number").toString();
        
        Path path = Path.of(shop.get("name")+"_"+ cardNumber + ".png");
        EncodingForCode encode;

        switch (shopId){
            case 1:
                encode = EncodingForCode.EAN_13;
                try {
                    CreateCode.createQR(cardNumber,encode.toString(),path);
                } catch (WriterException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                encode = EncodingForCode.QR_CODE;
                try {
                    CreateCode.createQR(cardNumber,encode.toString(),path);
                } catch (WriterException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        return path;
    }
}
