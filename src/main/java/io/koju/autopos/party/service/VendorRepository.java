package io.koju.autopos.party.service;

import io.koju.autopos.party.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Vendor entity.
 */
public interface VendorRepository extends JpaRepository<Vendor,Long> {

}
