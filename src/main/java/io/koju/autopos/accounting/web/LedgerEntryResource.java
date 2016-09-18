package io.koju.autopos.accounting.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.accounting.domain.LedgerEntry;
import io.koju.autopos.accounting.repo.LedgerEntryRepository;
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
 * REST controller for managing LedgerEntry.
 */
@RestController
@RequestMapping("/api")
public class LedgerEntryResource {

    private final Logger log = LoggerFactory.getLogger(LedgerEntryResource.class);
        
    @Inject
    private LedgerEntryRepository ledgerEntryRepository;
    
    /**
     * POST  /ledger-entries : Create a new ledgerEntry.
     *
     * @param ledgerEntry the ledgerEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ledgerEntry, or with status 400 (Bad Request) if the ledgerEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ledger-entries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LedgerEntry> createLedgerEntry(@Valid @RequestBody LedgerEntry ledgerEntry) throws URISyntaxException {
        log.debug("REST request to save LedgerEntry : {}", ledgerEntry);
        if (ledgerEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ledgerEntry", "idexists", "A new ledgerEntry cannot already have an ID")).body(null);
        }
        LedgerEntry result = ledgerEntryRepository.save(ledgerEntry);
        return ResponseEntity.created(new URI("/api/ledger-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ledgerEntry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ledger-entries : Updates an existing ledgerEntry.
     *
     * @param ledgerEntry the ledgerEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ledgerEntry,
     * or with status 400 (Bad Request) if the ledgerEntry is not valid,
     * or with status 500 (Internal Server Error) if the ledgerEntry couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ledger-entries",
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
     * GET  /ledger-entries : get all the ledgerEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ledgerEntries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/ledger-entries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LedgerEntry>> getAllLedgerEntries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LedgerEntries");
        Page<LedgerEntry> page = ledgerEntryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ledger-entries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ledger-entries/:id : get the "id" ledgerEntry.
     *
     * @param id the id of the ledgerEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ledgerEntry, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/ledger-entries/{id}",
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
     * DELETE  /ledger-entries/:id : delete the "id" ledgerEntry.
     *
     * @param id the id of the ledgerEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/ledger-entries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLedgerEntry(@PathVariable Long id) {
        log.debug("REST request to delete LedgerEntry : {}", id);
        ledgerEntryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ledgerEntry", id.toString())).build();
    }

}
