package io.koju.autopos.trade.service;

import io.koju.autopos.trade.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {

}
