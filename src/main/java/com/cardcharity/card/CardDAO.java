package com.cardcharity.card;

import com.cardcharity.IDao;
import com.cardcharity.exception.QueryException;
import com.cardcharity.owner.Owner;
import com.cardcharity.owner.OwnerDAO;
import com.cardcharity.shop.Shop;
import com.cardcharity.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CardDAO implements IDao<Card> {
    @Autowired
    CardRepository repository;
    @Autowired
    OwnerDAO ownerDao;
    @Autowired
    ShopRepository shopDao;

    public List<Card> findAll(String number, Long owner, Long shop) {
        Card card = new Card();
        card.setNumber(number);
        if(owner != null){
            card.setOwner(ownerDao.findByID(owner).get());
        }else {
            card.setOwner(null);
        }
        if (shop != null){
            card.setShop(shopDao.findById(shop).get());
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

    public Card findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Card> findAll() {
        return repository.findAll();
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

    public Card getCardFromWrapper(CardWrapper cardWrapper){
        Card newCard = new Card();
        if(cardWrapper.getId() != null) {
            newCard.setId(cardWrapper.getId());
        }
        newCard.setNumber(cardWrapper.getNumber());
        newCard.setOwner(ownerDao.findByID(cardWrapper.getOwner()).get());
        newCard.setShop(shopDao.findById(cardWrapper.getShop()).get());
        newCard.setActive(cardWrapper.isActive());
        return newCard;
    }

    public void save(Card card) throws QueryException {
        if(findById(card.getId()) != null) throw new QueryException("Card with id " + card.getId() + " already exists");
        repository.save(card);
    }
}
