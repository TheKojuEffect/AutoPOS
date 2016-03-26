package io.koju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.domain.PhoneNumber;
import io.koju.autopos.repository.PhoneNumberRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PhoneNumber.
 */
@RestController
@RequestMapping("/api")
public class PhoneNumberResource {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberResource.class);
        
    @Inject
    private PhoneNumberRepository phoneNumberRepository;
    
    /**
     * POST  /phone-numbers : Create a new phoneNumber.
     *
     * @param phoneNumber the phoneNumber to create
     * @return the ResponseEntity with status 201 (Created) and with body the new phoneNumber, or with status 400 (Bad Request) if the phoneNumber has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/phone-numbers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PhoneNumber> createPhoneNumber(@Valid @RequestBody PhoneNumber phoneNumber) throws URISyntaxException {
        log.debug("REST request to save PhoneNumber : {}", phoneNumber);
        if (phoneNumber.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("phoneNumber", "idexists", "A new phoneNumber cannot already have an ID")).body(null);
        }
        PhoneNumber result = phoneNumberRepository.save(phoneNumber);
        return ResponseEntity.created(new URI("/api/phone-numbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("phoneNumber", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /phone-numbers : Updates an existing phoneNumber.
     *
     * @param phoneNumber the phoneNumber to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated phoneNumber,
     * or with status 400 (Bad Request) if the phoneNumber is not valid,
     * or with status 500 (Internal Server Error) if the phoneNumber couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/phone-numbers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PhoneNumber> updatePhoneNumber(@Valid @RequestBody PhoneNumber phoneNumber) throws URISyntaxException {
        log.debug("REST request to update PhoneNumber : {}", phoneNumber);
        if (phoneNumber.getId() == null) {
            return createPhoneNumber(phoneNumber);
        }
        PhoneNumber result = phoneNumberRepository.save(phoneNumber);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("phoneNumber", phoneNumber.getId().toString()))
            .body(result);
    }

    /**
     * GET  /phone-numbers : get all the phoneNumbers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of phoneNumbers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/phone-numbers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PhoneNumber>> getAllPhoneNumbers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PhoneNumbers");
        Page<PhoneNumber> page = phoneNumberRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/phone-numbers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /phone-numbers/:id : get the "id" phoneNumber.
     *
     * @param id the id of the phoneNumber to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the phoneNumber, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/phone-numbers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PhoneNumber> getPhoneNumber(@PathVariable Long id) {
        log.debug("REST request to get PhoneNumber : {}", id);
        PhoneNumber phoneNumber = phoneNumberRepository.findOne(id);
        return Optional.ofNullable(phoneNumber)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /phone-numbers/:id : delete the "id" phoneNumber.
     *
     * @param id the id of the phoneNumber to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/phone-numbers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePhoneNumber(@PathVariable Long id) {
        log.debug("REST request to delete PhoneNumber : {}", id);
        phoneNumberRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("phoneNumber", id.toString())).build();
    }

}
