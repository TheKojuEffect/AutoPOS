package io.koju.autopos.repository;

import io.koju.autopos.domain.StockHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StockHistory entity.
 */
public interface StockHistoryRepository extends JpaRepository<StockHistory,Long> {

}
