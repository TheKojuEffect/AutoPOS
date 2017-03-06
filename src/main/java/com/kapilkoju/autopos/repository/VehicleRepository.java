package com.kapilkoju.autopos.repository;

import com.kapilkoju.autopos.domain.Vehicle;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Vehicle entity.
 */
@SuppressWarnings("unused")
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

}
