package com.cardcharity.history;

import com.cardcharity.IDao;
import com.cardcharity.card.Card;
import com.cardcharity.card.CardRepository;
import com.cardcharity.customer.Customer;
import com.cardcharity.customer.CustomerRepository;
import com.cardcharity.exception.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class HistoryDAO implements IDao<History> {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    HistoryRepository historyRepository;

    public History fromCurrentDate(Card card, Customer customer) {
        History history = new History();
        history.setCard(card);
        history.setCustomer(customer);
        history.setDate(LocalDate.now());
        return history;
    }


    public List<History> findAllByCardUserDate(Long cardId, Long userId, LocalDate startDate, LocalDate endDate) {
        History history = new History();

        if(startDate == null) startDate = LocalDate.MIN;
        if(endDate == null) endDate = LocalDate.MAX;

        if(cardId != null) history.setCard(cardRepository.findById(cardId).get());
        if (userId != null) history.setCustomer(customerRepository.findById(userId).get());
        Example<History> historyExample = Example.of(history, ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withIgnorePaths("date"));

        List<History> cards = historyRepository.findAll(historyExample);
        for (History h : cards) {
            if(!(h.getDate().isAfter(startDate) && h.getDate().isBefore(endDate))) {
                cards.remove(h);
            }
        }
        return cards;
    }

    @Override
    public List<History> findAll() {
        return historyRepository.findAll();
    }

    @Override
    public void update(History history) throws QueryException {
        throw new QueryException("History cannot be changed");
    }

    @Override
    public History findById(Long entityId) {
        return historyRepository.findById(entityId).orElse(null);
    }

    @Override
    public void save(History history) throws QueryException {
        if(!historyRepository.findById(history.getId()).isEmpty()) throw new QueryException("History with this ID already exists");
        historyRepository.save(history);
    }
}
