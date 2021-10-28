package com.cardcharity.card;

import com.cardcharity.owner.Owner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {

}
