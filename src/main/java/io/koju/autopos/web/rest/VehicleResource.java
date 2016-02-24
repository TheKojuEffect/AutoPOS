package io.koju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.domain.Vehicle;
import io.koju.autopos.repository.VehicleRepository;
import io.koju.autopos.web.rest.util.HeaderUtil;
import io.koju.autopos.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Vehicle.
 */
@RestController
@RequestMapping("/api")
public class VehicleResource {

    private final Logger log = LoggerFactory.getLogger(VehicleResource.class);
        
    @Inject
    private VehicleRepository vehicleRepository;
    
    /**
     * POST  /vehicles -> Create a new vehicle.
     */
    @RequestMapping(value = "/vehicles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) throws URISyntaxException {
        log.debug("REST request to save Vehicle : {}", vehicle);
        if (vehicle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vehicle", "idexists", "A new vehicle cannot already have an ID")).body(null);
        }
        Vehicle result = vehicleRepository.save(vehicle);
        return ResponseEntity.created(new URI("/api/vehicles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vehicle", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicles -> Updates an existing vehicle.
     */
    @RequestMapping(value = "/vehicles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Vehicle> updateVehicle(@Valid @RequestBody Vehicle vehicle) throws URISyntaxException {
        log.debug("REST request to update Vehicle : {}", vehicle);
        if (vehicle.getId() == null) {
            return createVehicle(vehicle);
        }
        Vehicle result = vehicleRepository.save(vehicle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vehicle", vehicle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicles -> get all the vehicles.
     */
    @RequestMapping(value = "/vehicles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Vehicle>> getAllVehicles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Vehicles");
        Page<Vehicle> page = vehicleRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vehicles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vehicles/:id -> get the "id" vehicle.
     */
    @RequestMapping(value = "/vehicles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Long id) {
        log.debug("REST request to get Vehicle : {}", id);
        Vehicle vehicle = vehicleRepository.findOne(id);
        return Optional.ofNullable(vehicle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vehicles/:id -> delete the "id" vehicle.
     */
    @RequestMapping(value = "/vehicles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        log.debug("REST request to delete Vehicle : {}", id);
        vehicleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vehicle", id.toString())).build();
    }
}
