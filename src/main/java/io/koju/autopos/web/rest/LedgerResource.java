package io.koju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.domain.Ledger;
import io.koju.autopos.service.LedgerService;
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
 * REST controller for managing Ledger.
 */
@RestController
@RequestMapping("/api")
public class LedgerResource {

    private final Logger log = LoggerFactory.getLogger(LedgerResource.class);
        
    @Inject
    private LedgerService ledgerService;
    
    /**
     * POST  /ledgers : Create a new ledger.
     *
     * @param ledger the ledger to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ledger, or with status 400 (Bad Request) if the ledger has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ledgers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ledger> createLedger(@Valid @RequestBody Ledger ledger) throws URISyntaxException {
        log.debug("REST request to save Ledger : {}", ledger);
        if (ledger.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ledger", "idexists", "A new ledger cannot already have an ID")).body(null);
        }
        Ledger result = ledgerService.save(ledger);
        return ResponseEntity.created(new URI("/api/ledgers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ledger", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ledgers : Updates an existing ledger.
     *
     * @param ledger the ledger to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ledger,
     * or with status 400 (Bad Request) if the ledger is not valid,
     * or with status 500 (Internal Server Error) if the ledger couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ledgers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ledger> updateLedger(@Valid @RequestBody Ledger ledger) throws URISyntaxException {
        log.debug("REST request to update Ledger : {}", ledger);
        if (ledger.getId() == null) {
            return createLedger(ledger);
        }
        Ledger result = ledgerService.save(ledger);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ledger", ledger.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ledgers : get all the ledgers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ledgers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/ledgers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Ledger>> getAllLedgers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Ledgers");
        Page<Ledger> page = ledgerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ledgers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ledgers/:id : get the "id" ledger.
     *
     * @param id the id of the ledger to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ledger, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/ledgers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ledger> getLedger(@PathVariable Long id) {
        log.debug("REST request to get Ledger : {}", id);
        Ledger ledger = ledgerService.findOne(id);
        return Optional.ofNullable(ledger)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ledgers/:id : delete the "id" ledger.
     *
     * @param id the id of the ledger to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/ledgers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLedger(@PathVariable Long id) {
        log.debug("REST request to delete Ledger : {}", id);
        ledgerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ledger", id.toString())).build();
    }

}
