package io.koju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.domain.DayBookEntry;
import io.koju.autopos.repository.DayBookEntryRepository;
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
 * REST controller for managing DayBookEntry.
 */
@RestController
@RequestMapping("/api")
public class DayBookEntryResource {

    private final Logger log = LoggerFactory.getLogger(DayBookEntryResource.class);
        
    @Inject
    private DayBookEntryRepository dayBookEntryRepository;
    
    /**
     * POST  /dayBookEntrys -> Create a new dayBookEntry.
     */
    @RequestMapping(value = "/dayBookEntrys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DayBookEntry> createDayBookEntry(@Valid @RequestBody DayBookEntry dayBookEntry) throws URISyntaxException {
        log.debug("REST request to save DayBookEntry : {}", dayBookEntry);
        if (dayBookEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dayBookEntry", "idexists", "A new dayBookEntry cannot already have an ID")).body(null);
        }
        DayBookEntry result = dayBookEntryRepository.save(dayBookEntry);
        return ResponseEntity.created(new URI("/api/dayBookEntrys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dayBookEntry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dayBookEntrys -> Updates an existing dayBookEntry.
     */
    @RequestMapping(value = "/dayBookEntrys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DayBookEntry> updateDayBookEntry(@Valid @RequestBody DayBookEntry dayBookEntry) throws URISyntaxException {
        log.debug("REST request to update DayBookEntry : {}", dayBookEntry);
        if (dayBookEntry.getId() == null) {
            return createDayBookEntry(dayBookEntry);
        }
        DayBookEntry result = dayBookEntryRepository.save(dayBookEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dayBookEntry", dayBookEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dayBookEntrys -> get all the dayBookEntrys.
     */
    @RequestMapping(value = "/dayBookEntrys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DayBookEntry>> getAllDayBookEntrys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DayBookEntrys");
        Page<DayBookEntry> page = dayBookEntryRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dayBookEntrys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dayBookEntrys/:id -> get the "id" dayBookEntry.
     */
    @RequestMapping(value = "/dayBookEntrys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DayBookEntry> getDayBookEntry(@PathVariable Long id) {
        log.debug("REST request to get DayBookEntry : {}", id);
        DayBookEntry dayBookEntry = dayBookEntryRepository.findOne(id);
        return Optional.ofNullable(dayBookEntry)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dayBookEntrys/:id -> delete the "id" dayBookEntry.
     */
    @RequestMapping(value = "/dayBookEntrys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDayBookEntry(@PathVariable Long id) {
        log.debug("REST request to delete DayBookEntry : {}", id);
        dayBookEntryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dayBookEntry", id.toString())).build();
    }
}
