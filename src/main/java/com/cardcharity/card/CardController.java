package com.cardcharity.card;

import com.cardcharity.owner.CardDAO;
import com.cardcharity.owner.OwnerDAO;
import com.cardcharity.shop.ShopDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/card")
public class CardController {
    @Autowired
    CardDAO cardDAO;

    @PostMapping("/post")
    public void postCard(@RequestBody CardWrapper card){
        cardDAO.save(card);
    }

    @GetMapping("/get")
    public List<Card> getOwnerWithFIOP(){
        return cardDAO.findAll();
    }

    @GetMapping("/get/{id}")
    public Optional<Card> getOwnerWithID(@PathVariable Long id){
        return cardDAO.findById(id);
    }
}
