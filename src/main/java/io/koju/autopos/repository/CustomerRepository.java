package io.koju.autopos.repository;

import io.koju.autopos.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Customer entity.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
