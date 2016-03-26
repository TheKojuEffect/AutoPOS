package io.koju.autopos.repository;

import io.koju.autopos.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {

}
