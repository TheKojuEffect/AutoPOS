package io.koju.autopos.shared.service;

import io.koju.autopos.shared.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Phone entity.
 */
public interface PhoneNumberRepository extends JpaRepository<Phone,Long> {

}
