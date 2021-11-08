package com.cardcharity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findOneByUid(String uid);
    List<Customer> findByEmail(String email);
}
