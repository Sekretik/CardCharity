package com.cardcharity.shop;

import com.cardcharity.code.Image;
import com.cardcharity.exception.QueryException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @GetMapping("/logo/{id}")
    public void getLogo(@PathVariable String id, HttpServletResponse response) throws QueryException {
        String shopName = "shopLogo_" + id + ".jpg";
        File file = new File("src/resources/logos/"+shopName);
        try {
            BufferedImage image = ImageIO.read(file);
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            throw new QueryException("this shop does not exist");
        }
    }
}
