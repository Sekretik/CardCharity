package com.cardcharity.history;

import com.cardcharity.IDao;
import com.cardcharity.card.Card;
import com.cardcharity.card.CardDAO;
import com.cardcharity.card.CardRepository;
import com.cardcharity.customer.Customer;
import com.cardcharity.customer.CustomerDAO;
import com.cardcharity.customer.CustomerRepository;
import com.cardcharity.exception.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryDAO implements IDao<History> {
    @Autowired
    CustomerDAO customerDAO;


    @Autowired
    CardDAO cardDAO;

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

        if(cardId != null) {
            Card card = cardDAO.findById(cardId);
            if(card == null) return new ArrayList<>();
            history.setCard(card);
        }
        if (userId != null) {
            Customer customer = customerDAO.findById(userId);
            if(customer == null) return new ArrayList<>();
            history.setCustomer(customer);
        }
        Example<History> historyExample = Example.of(history, ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withIgnorePaths("date"));

        List<History> resultList = historyRepository.findAll(historyExample);
        for (History h : resultList) {
            if(!(h.getDate().isAfter(startDate) && h.getDate().isBefore(endDate))) {
                resultList.remove(h);
            }
        }
        System.out.println("bruh");
        return resultList;
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
