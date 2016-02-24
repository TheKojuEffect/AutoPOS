package io.koju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.domain.LedgerEntry;
import io.koju.autopos.repository.LedgerEntryRepository;
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
 * REST controller for managing LedgerEntry.
 */
@RestController
@RequestMapping("/api")
public class LedgerEntryResource {

    private final Logger log = LoggerFactory.getLogger(LedgerEntryResource.class);
        
    @Inject
    private LedgerEntryRepository ledgerEntryRepository;
    
    /**
     * POST  /ledgerEntrys -> Create a new ledgerEntry.
     */
    @RequestMapping(value = "/ledgerEntrys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LedgerEntry> createLedgerEntry(@Valid @RequestBody LedgerEntry ledgerEntry) throws URISyntaxException {
        log.debug("REST request to save LedgerEntry : {}", ledgerEntry);
        if (ledgerEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ledgerEntry", "idexists", "A new ledgerEntry cannot already have an ID")).body(null);
        }
        LedgerEntry result = ledgerEntryRepository.save(ledgerEntry);
        return ResponseEntity.created(new URI("/api/ledgerEntrys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ledgerEntry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ledgerEntrys -> Updates an existing ledgerEntry.
     */
    @RequestMapping(value = "/ledgerEntrys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LedgerEntry> updateLedgerEntry(@Valid @RequestBody LedgerEntry ledgerEntry) throws URISyntaxException {
        log.debug("REST request to update LedgerEntry : {}", ledgerEntry);
        if (ledgerEntry.getId() == null) {
            return createLedgerEntry(ledgerEntry);
        }
        LedgerEntry result = ledgerEntryRepository.save(ledgerEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ledgerEntry", ledgerEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ledgerEntrys -> get all the ledgerEntrys.
     */
    @RequestMapping(value = "/ledgerEntrys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LedgerEntry>> getAllLedgerEntrys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LedgerEntrys");
        Page<LedgerEntry> page = ledgerEntryRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ledgerEntrys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ledgerEntrys/:id -> get the "id" ledgerEntry.
     */
    @RequestMapping(value = "/ledgerEntrys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LedgerEntry> getLedgerEntry(@PathVariable Long id) {
        log.debug("REST request to get LedgerEntry : {}", id);
        LedgerEntry ledgerEntry = ledgerEntryRepository.findOne(id);
        return Optional.ofNullable(ledgerEntry)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ledgerEntrys/:id -> delete the "id" ledgerEntry.
     */
    @RequestMapping(value = "/ledgerEntrys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLedgerEntry(@PathVariable Long id) {
        log.debug("REST request to delete LedgerEntry : {}", id);
        ledgerEntryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ledgerEntry", id.toString())).build();
    }
}
