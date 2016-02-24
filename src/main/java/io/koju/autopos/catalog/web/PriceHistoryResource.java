package io.koju.autopos.catalog.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.catalog.domain.PriceHistory;
import io.koju.autopos.catalog.service.PriceHistoryRepository;
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
 * REST controller for managing PriceHistory.
 */
@RestController
@RequestMapping("/api")
public class PriceHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PriceHistoryResource.class);

    @Inject
    private PriceHistoryRepository priceHistoryRepository;

    /**
     * POST  /priceHistorys -> Create a new priceHistory.
     */
    @RequestMapping(value = "/priceHistorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PriceHistory> createPriceHistory(@Valid @RequestBody PriceHistory priceHistory) throws URISyntaxException {
        log.debug("REST request to save PriceHistory : {}", priceHistory);
        if (priceHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("priceHistory", "idexists", "A new priceHistory cannot already have an ID")).body(null);
        }
        PriceHistory result = priceHistoryRepository.save(priceHistory);
        return ResponseEntity.created(new URI("/api/priceHistorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("priceHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /priceHistorys -> Updates an existing priceHistory.
     */
    @RequestMapping(value = "/priceHistorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PriceHistory> updatePriceHistory(@Valid @RequestBody PriceHistory priceHistory) throws URISyntaxException {
        log.debug("REST request to update PriceHistory : {}", priceHistory);
        if (priceHistory.getId() == null) {
            return createPriceHistory(priceHistory);
        }
        PriceHistory result = priceHistoryRepository.save(priceHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("priceHistory", priceHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /priceHistorys -> get all the priceHistorys.
     */
    @RequestMapping(value = "/priceHistorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PriceHistory>> getAllPriceHistorys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PriceHistorys");
        Page<PriceHistory> page = priceHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/priceHistorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /priceHistorys/:id -> get the "id" priceHistory.
     */
    @RequestMapping(value = "/priceHistorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PriceHistory> getPriceHistory(@PathVariable Long id) {
        log.debug("REST request to get PriceHistory : {}", id);
        PriceHistory priceHistory = priceHistoryRepository.findOne(id);
        return Optional.ofNullable(priceHistory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /priceHistorys/:id -> delete the "id" priceHistory.
     */
    @RequestMapping(value = "/priceHistorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePriceHistory(@PathVariable Long id) {
        log.debug("REST request to delete PriceHistory : {}", id);
        priceHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("priceHistory", id.toString())).build();
    }
}
