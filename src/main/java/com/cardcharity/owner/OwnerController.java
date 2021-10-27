package com.cardcharity.owner;

import com.cardcharity.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    OwnerDAO dao;

    @GetMapping("/get")
    public List<Owner> getOwnerWithFIO(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String surname,
                                       @RequestParam(required = false) String patronymic,
                                       @RequestParam(required = false) String passport){
        return dao.findByFIO(name,surname,patronymic,passport);
    }

    @PostMapping("/post")
    public void postOwner(@RequestBody Owner owner) throws ServerException {
        dao.create(owner);
    }

    @PutMapping("/put")
    public void putOwner(@RequestBody Owner owner) throws ServerException {
        dao.update(owner);
    }
}
