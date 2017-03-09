package com.kapilkoju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kapilkoju.autopos.domain.DayBookEntry;
import com.kapilkoju.autopos.service.DayBookEntryService;
import com.kapilkoju.autopos.web.rest.util.HeaderUtil;
import com.kapilkoju.autopos.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private static final String ENTITY_NAME = "dayBookEntry";
        
    private final DayBookEntryService dayBookEntryService;

    public DayBookEntryResource(DayBookEntryService dayBookEntryService) {
        this.dayBookEntryService = dayBookEntryService;
    }

    /**
     * POST  /day-book-entries : Create a new dayBookEntry.
     *
     * @param dayBookEntry the dayBookEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dayBookEntry, or with status 400 (Bad Request) if the dayBookEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/day-book-entries")
    @Timed
    public ResponseEntity<DayBookEntry> createDayBookEntry(@Valid @RequestBody DayBookEntry dayBookEntry) throws URISyntaxException {
        log.debug("REST request to save DayBookEntry : {}", dayBookEntry);
        if (dayBookEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dayBookEntry cannot already have an ID")).body(null);
        }
        DayBookEntry result = dayBookEntryService.save(dayBookEntry);
        return ResponseEntity.created(new URI("/api/day-book-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /day-book-entries : Updates an existing dayBookEntry.
     *
     * @param dayBookEntry the dayBookEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dayBookEntry,
     * or with status 400 (Bad Request) if the dayBookEntry is not valid,
     * or with status 500 (Internal Server Error) if the dayBookEntry couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/day-book-entries")
    @Timed
    public ResponseEntity<DayBookEntry> updateDayBookEntry(@Valid @RequestBody DayBookEntry dayBookEntry) throws URISyntaxException {
        log.debug("REST request to update DayBookEntry : {}", dayBookEntry);
        if (dayBookEntry.getId() == null) {
            return createDayBookEntry(dayBookEntry);
        }
        DayBookEntry result = dayBookEntryService.save(dayBookEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dayBookEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /day-book-entries : get all the dayBookEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dayBookEntries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/day-book-entries")
    @Timed
    public ResponseEntity<List<DayBookEntry>> getAllDayBookEntries(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DayBookEntries");
        Page<DayBookEntry> page = dayBookEntryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/day-book-entries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /day-book-entries/:id : get the "id" dayBookEntry.
     *
     * @param id the id of the dayBookEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dayBookEntry, or with status 404 (Not Found)
     */
    @GetMapping("/day-book-entries/{id}")
    @Timed
    public ResponseEntity<DayBookEntry> getDayBookEntry(@PathVariable Long id) {
        log.debug("REST request to get DayBookEntry : {}", id);
        DayBookEntry dayBookEntry = dayBookEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dayBookEntry));
    }

    /**
     * DELETE  /day-book-entries/:id : delete the "id" dayBookEntry.
     *
     * @param id the id of the dayBookEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/day-book-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteDayBookEntry(@PathVariable Long id) {
        log.debug("REST request to delete DayBookEntry : {}", id);
        dayBookEntryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
