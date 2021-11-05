package com.cardcharity.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
