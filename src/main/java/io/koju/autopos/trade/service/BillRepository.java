package io.koju.autopos.trade.service;

import io.koju.autopos.trade.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Bill entity.
 */
public interface BillRepository extends JpaRepository<Bill,Long> {

}
