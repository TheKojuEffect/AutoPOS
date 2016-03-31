package io.koju.autopos.party.service;

import io.koju.autopos.party.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Vehicle entity.
 */
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

}
