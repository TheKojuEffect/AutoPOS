package io.koju.autopos.repository;

import io.koju.autopos.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Stock entity.
 */
public interface StockRepository extends JpaRepository<Stock,Long> {

}
