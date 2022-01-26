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
        history.setDate(new Date());
        return history;
    }


    public List<HistoryWrapper> findAllByCardUser(Long cardId, Long userId, Date startDate, Date endDate) {
        History history = new History();
        if(cardId != null) history.setCard(cardRepository.findById(cardId).get());
        if (userId != null) history.setCustomer(customerRepository.findById(userId).get());
        Example<History> historyExample = Example.of(history, ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withIgnorePaths("date"));

        List<History> cards = historyRepository.findAll(getSpecFromDataAndExample(startDate, endDate, historyExample));
        List<HistoryWrapper> wrappers = new ArrayList<>();
        for (History h : cards) {
            wrappers.add(new HistoryWrapper(h));
        }
        return wrappers;
    }

    public Specification<History> getSpecFromDataAndExample(Date startDate, Date endDate, Example<History> example) {
        return (Specification<History>) (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if(startDate != null) predicates.add(builder.greaterThanOrEqualTo(root.get("date"), startDate));
            if(endDate != null) predicates.add(builder.lessThanOrEqualTo(root.get("date"), endDate));
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
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
