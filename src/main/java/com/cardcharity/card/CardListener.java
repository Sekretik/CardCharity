package com.cardcharity.card;

import com.cardcharity.exception.QueryException;
import com.cardcharity.owner.OwnerDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PreUpdate;

public class CardListener {

    @PreUpdate
    private void preUpdate(Card card) throws QueryException {
        System.out.println("keb");
        if(card.isActive() && !card.getOwner().isActive()) {
            System.out.println("Got here lolz");
            throw new QueryException("Cannot set card " +
                    card.getId() +
                    " to active: its owner(owners ID: " +
                    card.getOwner().getId() +
                    "is not active");
        }
    }
}
