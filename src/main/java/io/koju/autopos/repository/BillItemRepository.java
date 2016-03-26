package io.koju.autopos.repository;

import io.koju.autopos.domain.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the BillItem entity.
 */
public interface BillItemRepository extends JpaRepository<BillItem,Long> {

}
