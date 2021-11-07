package com.cardcharity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUid(String uid);
    List<User> findByEmail(String email);
}
