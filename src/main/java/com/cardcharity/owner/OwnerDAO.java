package com.cardcharity.owner;

import com.cardcharity.exception.ServerException;
import com.cardcharity.history.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OwnerDAO {
    @Autowired
    OwnerRepository repository;

    public List<Owner> findByFIOP(String name, String surname, String patronymic, String passport) {
        Owner owner = new Owner(passport,name,surname,patronymic);
        Example<Owner> historyExample = Example.of(owner, ExampleMatcher.matchingAll().withIgnoreNullValues()
                .withIgnorePaths("id")
                .withIgnorePaths("useCount")
                .withIgnorePaths("active"));
        return repository.findAll(historyExample);
    }
    public void increaseUseCount(Owner owner) {
        int ownerUseCount = owner.getUseCount();
        owner.setUseCount(ownerUseCount + 1);
        repository.save(owner);
    }

    public Optional<Owner> findByID(Long id){
        return repository.findById(id);
    }

    public List<Owner> findByActive(boolean active){
        return repository.findByActive(active);
    }

    public void create(Owner owner) throws ServerException {
        if(owner.getId() != 0){
            throw new ServerException("New owner's id is not 0");
        } else if(!repository.findByPassportNumber(owner.getPassportNumber()).isEmpty()) {
            throw new ServerException("Owner with this passport number already exists");
        }
        repository.save(owner);
    }

    public void update(Owner owner) throws ServerException {
        if(repository.findById(owner.getId()).isEmpty()){
            throw new ServerException("Owner doesn't exist");
        }
        repository.save(owner);
    }
}
