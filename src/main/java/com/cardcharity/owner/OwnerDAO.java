package com.cardcharity.owner;

import com.cardcharity.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OwnerDAO {
    @Autowired
    OwnerRepository repository;

    public List<Owner> findByFIOP(String name, String surname, String patronymic, String passport) {
        List<Owner> list = null;
        if(passport == null) {
            if (name == null && surname == null && patronymic == null) {
                list = (List<Owner>) repository.findAll();
            }
            else if(name != null && surname != null && patronymic != null){
                list = repository.findByNameAndSurnameAndPatronymic(name, surname, patronymic);
            }
            else if(name != null && surname != null){
                list = repository.findByNameAndSurname(name, surname);
            }
            else if(name != null && patronymic != null){
                list = repository.findByNameAndPatronymic(name, patronymic);
            }
            else if(name == null && surname != null && patronymic != null){
                list = repository.findBySurnameAndPatronymic(surname, patronymic);
            }
            else if(name != null){
                list = repository.findByName(name);
            }
            else if(surname != null){
                list = repository.findBySurname(surname);
            }
            else if(patronymic != null){
                list = repository.findByPatronymic(patronymic);
            }
        }else {
            list = repository.findByPassportNumber(passport);
        }
        return list;
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
