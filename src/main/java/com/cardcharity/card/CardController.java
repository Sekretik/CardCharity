package com.cardcharity.card;

import com.cardcharity.exception.QueryException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/card")
@SecurityRequirement(name = "admin")
@CrossOrigin
public class CardController {
    @Autowired
    private CardDAO cardDAO;

    @PostMapping
    public Card postCard(@RequestBody CardWrapper cardWrapper) throws QueryException {
        Card newCard = cardDAO.getCardFromWrapper(cardWrapper);
        newCard.setId(null);
        cardDAO.save(newCard);
        return newCard;
    }

    @PutMapping("/{id}")
    public Card putCard(@PathVariable Long id, @RequestBody CardWrapper cardWrapper) throws QueryException {
        Card updatedCard = cardDAO.getCardFromWrapper(cardWrapper);
        updatedCard.setId(id);
        cardDAO.update(updatedCard);
        return updatedCard;
    }

    @GetMapping
    public List<Card> getOwnerWithFIOP(@RequestParam(required = false) String number,
                                       @RequestParam(required = false) Long owner,
                                       @RequestParam(required = false) Long shop){
        return cardDAO.findAll(number,owner,shop);
    }

    @GetMapping("/{id}")
    public Card getCardWithId(@PathVariable Long id){
        return cardDAO.findById(id);
    }
}
