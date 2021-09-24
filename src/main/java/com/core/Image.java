package com.core;

import com.codeGenerations.CreateCode;
import com.codeGenerations.EncodingForCode;
import com.database.DataBaseConnectivity;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.nio.file.Path;

public class Image {

    public static Path getImagePath(String cardNumber,int shopId){
        Path path = Path.of(cardNumber + ".png");
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
