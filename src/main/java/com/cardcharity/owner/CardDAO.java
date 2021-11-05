package com.cardcharity.owner;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardRepository;
import com.cardcharity.card.CardWrapper;
import com.cardcharity.shop.Shop;
import com.cardcharity.shop.ShopDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CardDAO {
    @Autowired
    CardRepository repository;
    @Autowired
    OwnerDAO ownerDAO;
    @Autowired
    ShopDAO shopDAO;

    public void save(CardWrapper card) {
        Card newCard = new Card();
        newCard.setNumber(card.getCardNumber());
        newCard.setOwner(ownerDAO.findByID(card.getOwner()).get());
        newCard.setShop(shopDAO.findById(card.getShop()).get());
        repository.save(newCard);
    }

    public List<Card> findAll() {
        return (List<Card>) repository.findAll();
    }

    public Optional<Card> findById(Long id) {
        return repository.findById(id);
    }

    public Card getCardWithMinUse(Shop shop){
        return repository.findByOwnerMinUseAndShop(shop).get(0);
    }
}
