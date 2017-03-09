package com.kapilkoju.autopos.service.impl;

import com.kapilkoju.autopos.service.VehicleService;
import com.kapilkoju.autopos.domain.Vehicle;
import com.kapilkoju.autopos.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Vehicle.
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService{

    private final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);
    
    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Save a vehicle.
     *
     * @param vehicle the entity to save
     * @return the persisted entity
     */
    @Override
    public Vehicle save(Vehicle vehicle) {
        log.debug("Request to save Vehicle : {}", vehicle);
        Vehicle result = vehicleRepository.save(vehicle);
        return result;
    }

    /**
     *  Get all the vehicles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vehicle> findAll(Pageable pageable) {
        log.debug("Request to get all Vehicles");
        Page<Vehicle> result = vehicleRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one vehicle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Vehicle findOne(Long id) {
        log.debug("Request to get Vehicle : {}", id);
        Vehicle vehicle = vehicleRepository.findOne(id);
        return vehicle;
    }

    /**
     *  Delete the  vehicle by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vehicle : {}", id);
        vehicleRepository.delete(id);
    }
}
