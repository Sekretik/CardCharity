package com.cardcharity.owner;

import com.cardcharity.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/owner")
public class OwnerController {
    @Autowired
    OwnerDAO dao;

    @GetMapping("/get")
    public List<Owner> getOwnerWithFIOP(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String surname,
                                       @RequestParam(required = false) String patronymic,
                                       @RequestParam(required = false) String passport){
        return dao.findByFIOP(name,surname,patronymic,passport);
    }

    @GetMapping("/get/{id}")
    public Optional<Owner> getOwnerWithID(@PathVariable Long id){
        return dao.findByID(id);
    }

    @PostMapping("/post")
    public void postOwner(@Valid @RequestBody Owner owner) throws ServerException {
        dao.create(owner);
    }

    @PutMapping("/put")
    public void putOwner(@Valid @RequestBody Owner owner) throws ServerException {
        dao.update(owner);
    }
}
