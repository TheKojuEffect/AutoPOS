package io.koju.autopos.trade.service;

import io.koju.autopos.trade.domain.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the BillItem entity.
 */
public interface BillItemRepository extends JpaRepository<BillItem,Long> {

}