package com.cardcharity.code;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardDAO;
import com.cardcharity.owner.OwnerDAO;
import com.cardcharity.shop.ShopDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("user/code")
public class CodeController {
    @Autowired
    CardDAO cardDAO;
    @Autowired
    ShopDAO shopDAO;
    @Autowired
    OwnerDAO ownerDAO;

    @GetMapping("/{shopId}")
    public void getCode(@PathVariable Long shopId, HttpServletResponse response){
        Card card = cardDAO.getCardWithMinUse(shopDAO.findById(shopId).get());
        ownerDAO.increaseUseCount(ownerDAO.findByID(card.getOwner().getId()).get());
        BufferedImage image = Image.createQR(card.getNumber());
        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
