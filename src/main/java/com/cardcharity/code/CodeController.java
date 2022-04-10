package com.cardcharity.code;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardDAO;
import com.cardcharity.exception.QueryException;
import com.cardcharity.history.HistoryDAO;
import com.cardcharity.owner.OwnerDAO;
import com.cardcharity.shop.Shop;
import com.cardcharity.customer.Customer;
import com.cardcharity.customer.CustomerDAO;
import com.cardcharity.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("user/code")
@CrossOrigin
public class CodeController {
    @Autowired
    CardDAO cardDao;
    @Autowired
    ShopRepository shopDao;
    @Autowired
    OwnerDAO ownerDao;
    @Autowired
    HistoryDAO historyDao;
    @Autowired
    CustomerDAO customerDao;

    @GetMapping()
    public void getCode(@RequestParam Long shopId, @RequestParam String uid, HttpServletResponse response) throws QueryException {
        Shop shop = shopDao.findById(shopId).get();
        Card card = cardDao.getCardWithMinUse(shop);
        Customer customer = null;
        boolean isInLocalDb = customerDao.findByUid(uid).isPresent();

        if(!isInLocalDb){
            customer = customerDao.getFromFirebase(uid);
            customerDao.save(customer);
        }else if(isInLocalDb){
            customer = customerDao.findByUid(uid).get();
        }


        historyDao.fromCurrentDate(card, customer);

        ownerDao.increaseUseCount(ownerDao.findByID(card.getOwner().getId()).get());
        customerDao.increaseUseCount(customer);
        BufferedImage image = Image.createQR(card.getNumber());
        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
