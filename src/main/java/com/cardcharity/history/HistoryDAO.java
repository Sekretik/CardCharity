package com.cardcharity.history;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardRepository;
import com.cardcharity.customer.Customer;
import com.cardcharity.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HistoryDAO {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    HistoryRepository historyRepository;

    public void save(Card card, Customer customer) {
        History history = new History();
        history.setCard(card);
        history.setCustomer(customer);
        history.setDate(new Date());
        historyRepository.save(history);
    }

    public List<HistoryWrapper> findAll(Long cardId, Long userId) {
        History history = new History();
        if(cardId != null){
            history.setCard(cardRepository.findById(cardId).get());
        }else {
            history.setCard(null);
        }
        if (userId != null){
            history.setCustomer(customerRepository.findById(userId).get());
        }else {
            history.setCustomer(null);
        }
        Example<History> historyExample = Example.of(history, ExampleMatcher.matchingAll().withIgnoreNullValues()
                .withIgnorePaths("id")
                .withIgnorePaths("data"));

        List<History> cards = historyRepository.findAll(historyExample);
        List<HistoryWrapper> wrappers = new ArrayList<>();
        for (History h : cards) {
            wrappers.add(new HistoryWrapper(h));
        }
        return wrappers;
    }
}
