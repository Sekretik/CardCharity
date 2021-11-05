package com.cardcharity.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findByNameIgnoreCase(String name);
    List<Owner> findBySurnameIgnoreCase(String surname);
    List<Owner> findByPatronymicIgnoreCase(String patronymic);
    List<Owner> findByNameAndSurnameIgnoreCase(String name, String surname);
    List<Owner> findByNameAndPatronymicIgnoreCase(String name, String patronymic);
    List<Owner> findBySurnameAndPatronymicIgnoreCase(String surname, String patronymic);
    List<Owner> findByNameAndSurnameAndPatronymicIgnoreCase(String name, String surname, String patronymic);
    List<Owner> findByPassportNumber(String passportNumber);
    List<Owner> findByActive(boolean active);
    boolean existsById(long id);
}
