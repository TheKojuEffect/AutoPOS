package io.koju.autopos.repository;

import io.koju.autopos.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Vehicle entity.
 */
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

}
