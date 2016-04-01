package io.koju.autopos.party.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.party.domain.Vehicle;
import io.koju.autopos.party.service.VehicleRepository;
import io.koju.autopos.web.rest.util.HeaderUtil;
import io.koju.autopos.web.rest.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(VehicleApi.API_VEHICLES)
@Slf4j
public class VehicleApi {

    static final String API_VEHICLES = "/api/vehicles";

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleApi(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) throws URISyntaxException {
        log.debug("REST request to save Vehicle : {}", vehicle);
        if (vehicle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vehicle", "idexists", "A new vehicle cannot already have an ID")).body(null);
        }
        Vehicle result = vehicleRepository.save(vehicle);
        return ResponseEntity.created(new URI(API_VEHICLES + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("vehicle", result.getId().toString()))
                .body(result);
    }


    @RequestMapping(method = PUT, produces = APPLICATION_JSON_VALUE)
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

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Vehicle>> getAllVehicles(Pageable pageable)
            throws URISyntaxException {
        log.debug("REST request to get a page of Vehicles");
        Page<Vehicle> page = vehicleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, API_VEHICLES);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
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


    @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        log.debug("REST request to delete Vehicle : {}", id);
        vehicleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vehicle", id.toString())).build();
    }

}
