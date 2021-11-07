package com.cardcharity.history;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardRepository;
import com.cardcharity.user.User;
import com.cardcharity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class HistoryDAO {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    HistoryRepository historyRepository;

    public List<History> getHistoryListByCardOrUser(long cardId, long userId) {
        History history = new History();
        history.setCard(cardRepository.findById(cardId).get());
        history.setUser(userRepository.findById(userId).get());
        Example<History> historyExample = Example.of(history, ExampleMatcher.matchingAll().withIgnoreNullValues()
                .withIgnorePaths("id"));
        return historyRepository.findAll(historyExample);
    }

    public void save(Card card, User user) {
        History history = new History();
        history.setCard(card);
        history.setUser(user);
        history.setDate(new Date());
        historyRepository.save(history);
    }
}
