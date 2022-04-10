package com.cardcharity.owner;

import com.cardcharity.exception.QueryException;
import com.cardcharity.exception.ServerException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/owner")
@SecurityRequirement(name = "admin")
@CrossOrigin
public class OwnerController {
    @Autowired
    OwnerDAO dao;

    @GetMapping
    public List<Owner> getOwnerWithFIOP(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String surname,
                                       @RequestParam(required = false) String patronymic,
                                       @RequestParam(required = false) String passport){
        return dao.findByFIOP(name,surname,patronymic,passport);
    }

    @GetMapping("/{id}")
    public Optional<Owner> getOwnerWithID(@PathVariable Long id){
        return dao.findByID(id);
    }

    @PostMapping
    public Owner postOwner(@Valid @RequestBody Owner owner) throws QueryException {
        owner.setId(null);
        dao.create(owner);
        return owner;
    }

    @PutMapping
    public Owner putOwner(@Valid @RequestBody Owner owner) throws QueryException {
        dao.update(owner);
        return owner;
    }
}
