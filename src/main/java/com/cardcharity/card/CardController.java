package com.cardcharity.card;

import com.cardcharity.exception.QueryException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/card")
@SecurityRequirement(name = "admin")
@CrossOrigin
public class CardController {
    @Autowired
    CardDAO cardDAO;

    @PostMapping
    public Optional<Card> postCard(@RequestBody CardWrapper card) throws QueryException {
        cardDAO.create(card);
        return cardDAO.findById(card.getId());
    }

    @PutMapping("/put")
    public Optional<Card> putCard(@RequestBody CardWrapper card) throws QueryException {
        cardDAO.update(card);
        return cardDAO.findById(card.getId());
    }

    @GetMapping
    public List<CardWrapper> getOwnerWithFIOP(@RequestParam(required = false) String number,
                                       @RequestParam(required = false) Long owner,
                                       @RequestParam(required = false) Long shop){
        return cardDAO.findAll(number,owner,shop);
    }

    @GetMapping("/{id}")
    public Optional<Card> getOwnerWithID(@PathVariable Long id){
        return cardDAO.findById(id);
    }
}
