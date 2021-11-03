package com.cardcharity.card;

import com.cardcharity.owner.Owner;
import com.cardcharity.shop.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {
    List<Card> findByNumber(String number);
    List<Card> findByOwner(Owner owner);
    List<Card> findByShop(Shop shop);

    List<Card> findByNumberAndShop(String number, Shop shop);
    List<Card> findByNumberAndOwner(String number, Owner owner);
    List<Card> findByOwnerAndShop(String number, Shop shop);

    @Query("SELECT c FROM Card c WHERE c.shop = :shop ORDER BY c.owner.useCount ASC")
    List<Card> findByOwnerMinUseAndShop(@Param("shop")Shop shop);
}
