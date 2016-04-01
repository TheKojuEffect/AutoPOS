package io.koju.autopos.trade.service;

import io.koju.autopos.trade.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Payment entity.
 */
public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
