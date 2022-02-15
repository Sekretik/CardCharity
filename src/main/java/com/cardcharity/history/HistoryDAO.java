package com.cardcharity.history;

import com.cardcharity.base.IDao;
import com.cardcharity.card.Card;
import com.cardcharity.card.CardRepository;
import com.cardcharity.customer.Customer;
import com.cardcharity.customer.CustomerRepository;
import com.cardcharity.exception.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static antlr.build.ANTLR.root;

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


    public List<HistoryWrapper> findAllByCardUserDate(Long cardId, Long userId, LocalDate startDate, LocalDate endDate) {
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
        List<HistoryWrapper> wrappers = new ArrayList<>();
        for (History h : cards) {
            if(h.getDate().isAfter(startDate) && h.getDate().isBefore(endDate)) {
                wrappers.add(new HistoryWrapper(h));
            }
        }
        return wrappers;
    }

    @Override
    public Optional<History> findById(long id) {
        return historyRepository.findById(id);
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
    public void save(History history) throws QueryException {
        if(!historyRepository.findById(history.getId()).isEmpty()) throw new QueryException("History with this ID already exists");
        historyRepository.save(history);
    }
}
