package com.codeGenerations;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.IOException;
import java.nio.file.Path;

public class CreateCode {

    public static void createQR(String data, String encoding, Path path) throws WriterException, IOException {
        int width = 500;
        int height = 250;

        if(encoding.equals("QR_CODE")){
            height = 500;
        }
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.valueOf(encoding), width, height);

        MatrixToImageWriter.writeToPath(matrix, "png", path);
    }
}
