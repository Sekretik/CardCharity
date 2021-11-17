package com.cardcharity.card;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardRepository;
import com.cardcharity.card.CardWrapper;
import com.cardcharity.exception.QueryException;
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

    public void create(CardWrapper card) throws QueryException {
        if(card.getId() != 0){
            throw new QueryException("New card's id is not 0");
        }
        save(card);
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
            wrappers.add(new CardWrapper(c));
        }
        return wrappers;
    }

    public Optional<Card> findById(Long id) {
        return repository.findById(id);
    }

    public Card getCardWithMinUse(Shop shop) throws QueryException {
        Card card = null;
        try {
            repository.findByOwnerMinUseAndShop(shop).get(0);
        }catch (Exception e){
            throw new QueryException("Card with this shop does not exist");
        }
        return card;
    }

    public void update(CardWrapper card) throws QueryException {
        if(repository.findById(card.getId()).isEmpty()){
            throw new QueryException("Card doesn't exist");
        }
        save(card);
    }

    private void save(CardWrapper card){
        Card newCard = new Card();
        if(card.getId() != 0) {
            newCard.setId(card.getId());
        }
        newCard.setNumber(card.getCardNumber());
        newCard.setOwner(ownerDAO.findByID(card.getOwner()).get());
        newCard.setShop(shopDAO.findById(card.getShop()).get());
        repository.save(newCard);
    }
}
