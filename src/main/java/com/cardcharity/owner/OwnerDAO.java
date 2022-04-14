package com.cardcharity.owner;

import com.cardcharity.IDao;
import com.cardcharity.card.Card;
import com.cardcharity.card.CardDAO;
import com.cardcharity.exception.QueryException;
import com.cardcharity.exception.ServerException;
import com.cardcharity.history.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Component
public class OwnerDAO implements IDao<Owner> {
    @Autowired
    OwnerRepository repository;

    @Autowired
    CardDAO cardDAO;

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

    public Owner findByID(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<Owner> findByActive(boolean active){
        return repository.findByActive(active);
    }

    public void create(Owner owner) throws QueryException {
        if(owner.getId() != null && repository.existsById(owner.getId())) throw new QueryException("Owner with id " + owner.getId() + " already exists");
        repository.save(owner);
    }

    @Override
    public void save(@Valid Owner owner) throws QueryException {
        repository.save(owner);
    }

    public void update(@Valid  Owner owner) throws QueryException {
        if(repository.findById(owner.getId()).isEmpty()){
            throw new QueryException("Owner doesn't exist");
        }
        if(!owner.isActive()) {
            for (Card card:cardDAO.findByOwner(owner)
                 ) {
                card.setActive(false);
                cardDAO.update(card);
            }
        }
        repository.save(owner);
    }

    @Override
    public Owner findById(Long entityId) {
        return repository.findById(entityId).orElse(null);
    }

    @Override
    public List<Owner> findAll() {
        return repository.findAll();
    }
}
