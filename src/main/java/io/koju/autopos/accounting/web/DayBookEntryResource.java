package io.koju.autopos.accounting.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.accounting.domain.DayBookEntry;
import io.koju.autopos.accounting.service.DayBookEntryRepository;
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
 * REST controller for managing DayBookEntry.
 */
@RestController
@RequestMapping("/api")
public class DayBookEntryResource {

    private final Logger log = LoggerFactory.getLogger(DayBookEntryResource.class);
        
    @Inject
    private DayBookEntryRepository dayBookEntryRepository;
    
    /**
     * POST  /day-book-entries : Create a new dayBookEntry.
     *
     * @param dayBookEntry the dayBookEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dayBookEntry, or with status 400 (Bad Request) if the dayBookEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/day-book-entries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DayBookEntry> createDayBookEntry(@Valid @RequestBody DayBookEntry dayBookEntry) throws URISyntaxException {
        log.debug("REST request to save DayBookEntry : {}", dayBookEntry);
        if (dayBookEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dayBookEntry", "idexists", "A new dayBookEntry cannot already have an ID")).body(null);
        }
        DayBookEntry result = dayBookEntryRepository.save(dayBookEntry);
        return ResponseEntity.created(new URI("/api/day-book-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dayBookEntry", result.getId().toString()))
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
    @RequestMapping(value = "/day-book-entries",
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
     * GET  /day-book-entries : get all the dayBookEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dayBookEntries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/day-book-entries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DayBookEntry>> getAllDayBookEntries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DayBookEntries");
        Page<DayBookEntry> page = dayBookEntryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/day-book-entries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /day-book-entries/:id : get the "id" dayBookEntry.
     *
     * @param id the id of the dayBookEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dayBookEntry, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/day-book-entries/{id}",
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
     * DELETE  /day-book-entries/:id : delete the "id" dayBookEntry.
     *
     * @param id the id of the dayBookEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/day-book-entries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDayBookEntry(@PathVariable Long id) {
        log.debug("REST request to delete DayBookEntry : {}", id);
        dayBookEntryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dayBookEntry", id.toString())).build();
    }

}
