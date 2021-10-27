package com.cardcharity.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    OwnerRepository repository;

    @GetMapping("/get")
    public List<Owner> getOwnerWithFIO(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String surname,
                                       @RequestParam(required = false) String patronymic){
        return repository.findByNameAndSurnameAndPatronymic(name, surname, patronymic);
    }

    @PostMapping("/post")
    public void postOwner(@RequestBody Owner owner){
        repository.save(owner);
    }
}
