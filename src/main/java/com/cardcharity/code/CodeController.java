package com.cardcharity.code;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardDAO;
import com.cardcharity.history.HistoryDAO;
import com.cardcharity.owner.OwnerDAO;
import com.cardcharity.shop.Shop;
import com.cardcharity.shop.ShopDAO;
import com.cardcharity.user.Customer;
import com.cardcharity.user.CustomerDAO;
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
    CustomerDAO customerDAO;

    @GetMapping("/{shopId}")
    public void getCode(@PathVariable Long shopId, @RequestParam String uid, HttpServletResponse response){
        Shop shop = shopDAO.findById(shopId).get();
        Card card = cardDAO.getCardWithMinUse(shop);
        Customer customer = customerDAO.findByUid(uid).get();

        if(customer == null){
            customer = customerDAO.getFromFirebase(uid);
            customerDAO.save(customer);
        }

        historyDAO.save(card, customer);

        ownerDAO.increaseUseCount(ownerDAO.findByID(card.getOwner().getId()).get());
        customerDAO.increaseUseCount(customer);
        BufferedImage image = Image.createQR(card.getNumber());
        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
