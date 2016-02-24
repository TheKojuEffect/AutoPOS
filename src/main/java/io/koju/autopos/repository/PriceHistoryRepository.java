package io.koju.autopos.repository;

import io.koju.autopos.domain.PriceHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PriceHistory entity.
 */
public interface PriceHistoryRepository extends JpaRepository<PriceHistory,Long> {

}
