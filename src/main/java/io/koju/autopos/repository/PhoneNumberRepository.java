package io.koju.autopos.repository;

import io.koju.autopos.domain.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the PhoneNumber entity.
 */
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber,Long> {

}
