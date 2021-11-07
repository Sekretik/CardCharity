package com.cardcharity.code;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardDAO;
import com.cardcharity.history.HistoryDAO;
import com.cardcharity.owner.OwnerDAO;
import com.cardcharity.shop.Shop;
import com.cardcharity.shop.ShopDAO;
import com.cardcharity.user.User;
import com.cardcharity.user.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    HistoryDAO historyDAO;
    @Autowired
    UserDAO userDAO;

    @GetMapping("/{shopId}")
    public void getCode(@PathVariable Long shopId, @RequestParam String uid, HttpServletResponse response){
        Shop shop = shopDAO.findById(shopId).get();
        Card card = cardDAO.getCardWithMinUse(shop);
        User user = userDAO.findByUid(uid).get();

        if(user == null){
            user = userDAO.getFromFirebase(uid);
            userDAO.save(user);
        }

        historyDAO.save(card,user);

        ownerDAO.increaseUseCount(ownerDAO.findByID(card.getOwner().getId()).get());
        userDAO.increaseUseCount(user);
        BufferedImage image = Image.createQR(card.getNumber());
        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
