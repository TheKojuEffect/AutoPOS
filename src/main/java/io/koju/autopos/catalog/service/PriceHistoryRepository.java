package io.koju.autopos.catalog.service;

import io.koju.autopos.catalog.domain.PriceHistory;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the PriceHistory entity.
 */
public interface PriceHistoryRepository extends JpaRepository<PriceHistory,Long> {

}
