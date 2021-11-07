package com.cardcharity.card;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardRepository;
import com.cardcharity.card.CardWrapper;
import com.cardcharity.owner.Owner;
import com.cardcharity.owner.OwnerDAO;
import com.cardcharity.shop.Shop;
import com.cardcharity.shop.ShopDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public List<CardWrapper> findAll(String number, Long owner, Long shop) {
        Card card = new Card();
        card.setNumber(number);
        if(owner != null){
            card.setOwner(ownerDAO.findByID(owner).get());
        }else {
            card.setOwner(null);
        }
        if (shop != null){
            card.setShop(shopDAO.findById(shop).get());
        }else {
            card.setShop(null);
        }
        Example<Card> historyExample = Example.of(card, ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withIgnorePaths("active"));
        List<Card> cards = repository.findAll(historyExample);
        List<CardWrapper> wrappers = new ArrayList<>();
        for (Card c : cards) {
            wrappers.add(new CardWrapper(c.getNumber(),c.getShop().getId(),c.getOwner().getId()));
        }
        return wrappers;
    }

    public Optional<Card> findById(Long id) {
        return repository.findById(id);
    }

    public Card getCardWithMinUse(Shop shop){
        return repository.findByOwnerMinUseAndShop(shop).get(0);
    }
}
