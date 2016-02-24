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
import org.springframework.web.bind.annotation.*;

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
     * POST  /ledgers -> Create a new ledger.
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
     * PUT  /ledgers -> Updates an existing ledger.
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
     * GET  /ledgers -> get all the ledgers.
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
     * GET  /ledgers/:id -> get the "id" ledger.
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
     * DELETE  /ledgers/:id -> delete the "id" ledger.
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
