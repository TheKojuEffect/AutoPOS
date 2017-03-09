package com.kapilkoju.autopos.repository;

import com.kapilkoju.autopos.domain.PriceHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PriceHistory entity.
 */
@SuppressWarnings("unused")
public interface PriceHistoryRepository extends JpaRepository<PriceHistory,Long> {

}
