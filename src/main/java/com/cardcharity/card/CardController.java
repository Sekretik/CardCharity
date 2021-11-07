package com.cardcharity.card;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/card")
@SecurityRequirement(name = "admin")

public class CardController {
    @Autowired
    CardDAO cardDAO;

    @PostMapping("/post")
    public void postCard(@RequestBody CardWrapper card){
        cardDAO.save(card);
    }

    @GetMapping("/get")
    public List<CardWrapper> getOwnerWithFIOP(@RequestParam(required = false) String number,
                                       @RequestParam(required = false) Long owner,
                                       @RequestParam(required = false) Long shop){
        return cardDAO.findAll(number,owner,shop);
    }

    @GetMapping("/get/{id}")
    public Optional<Card> getOwnerWithID(@PathVariable Long id){
        return cardDAO.findById(id);
    }
}
