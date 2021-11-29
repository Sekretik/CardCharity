package com.cardcharity.owner;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PostUpdate;
import java.util.List;

public class OwnerListener {

    @Autowired
    CardDAO cardDAO;

    @PostUpdate
    private void afterOwnerUpdate(Owner owner) {
        if(!owner.isActive()) {
            List<Card> cardList = cardDAO.findAll(null, owner.getId(), null);
            for (Card card : cardList
            ) {
                card.setActive(false);
                cardDAO.save(card);
            }
        }
    }
}
