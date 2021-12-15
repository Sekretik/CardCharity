package com.cardcharity.card;

import com.cardcharity.exception.QueryException;
import com.cardcharity.owner.Owner;
import com.cardcharity.owner.OwnerDAO;
import com.cardcharity.shop.Shop;
import com.cardcharity.shop.ShopDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public List<Card> findAll(String number, Long owner, Long shop) {
        Card card = new Card();
        card.setNumber(number);
        if(owner != null){
            card.setOwner(ownerDAO.findById(owner).get());
        }else {
            card.setOwner(null);
        }
        if (shop != null){
            card.setShop(shopDAO.findById(shop).get());
        }else {
            card.setShop(null);
        }
        Example<Card> cardExample = Example.of(card, ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withIgnorePaths("active"));
        List<Card> cards = repository.findAll(cardExample);
        return cards;
    }

    public List<Card> findByOwner(Owner owner) {
        return repository.findByOwner(owner);
    }

    public Optional<Card> findById(Long id) {
        return repository.findById(id);
    }

    public Card getCardWithMinUse(Shop shop) throws QueryException {
        Card card = null;
        try {
            card = repository.findByOwnerMinUseAndShop(shop).get(0);
        }catch (Exception e){
            throw new QueryException("Card with this shop does not exist");
        }
        return card;
    }

    public void update(Card card) throws QueryException {
        if(repository.findById(card.getId()).isEmpty()){
            throw new QueryException("Card doesn't exist");
        }
        save(card);
    }

    public Card getCardFromWrapper(CardWrapper cardWrapper, long id){
        Card newCard = new Card();
        if(id != 0) {
            newCard.setId(id);
        }
        newCard.setNumber(cardWrapper.getNumber());
        newCard.setOwner(ownerDAO.findById(cardWrapper.getOwner()).get());
        newCard.setShop(shopDAO.findById(cardWrapper.getShop()).get());
        newCard.setActive(cardWrapper.isActive());
        return newCard;
    }

    public void save(Card card) {
        repository.save(card);
    }
}
