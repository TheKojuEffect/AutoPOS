package io.koju.autopos.repository;

import io.koju.autopos.domain.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the StockHistory entity.
 */
public interface StockHistoryRepository extends JpaRepository<StockHistory,Long> {

}
