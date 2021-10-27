package com.cardcharity.owner;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    List<Owner> findByName(String name);
    List<Owner> findBySurname(String surname);
    List<Owner> findByPatronymic(String patronymic);
    List<Owner> findByNameAndSurname(String name, String surname);
    List<Owner> findByNameAndPatronymic(String name, String patronymic);
    List<Owner> findBySurnameAndPatronymic(String surname, String patronymic);
    List<Owner> findByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);
    List<Owner> findByPassportNumber(String passportNumber);
}
