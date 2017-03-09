package com.kapilkoju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kapilkoju.autopos.domain.PriceHistory;
import com.kapilkoju.autopos.service.PriceHistoryService;
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
 * REST controller for managing PriceHistory.
 */
@RestController
@RequestMapping("/api")
public class PriceHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PriceHistoryResource.class);

    private static final String ENTITY_NAME = "priceHistory";
        
    private final PriceHistoryService priceHistoryService;

    public PriceHistoryResource(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    /**
     * POST  /price-histories : Create a new priceHistory.
     *
     * @param priceHistory the priceHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priceHistory, or with status 400 (Bad Request) if the priceHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/price-histories")
    @Timed
    public ResponseEntity<PriceHistory> createPriceHistory(@Valid @RequestBody PriceHistory priceHistory) throws URISyntaxException {
        log.debug("REST request to save PriceHistory : {}", priceHistory);
        if (priceHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new priceHistory cannot already have an ID")).body(null);
        }
        PriceHistory result = priceHistoryService.save(priceHistory);
        return ResponseEntity.created(new URI("/api/price-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /price-histories : Updates an existing priceHistory.
     *
     * @param priceHistory the priceHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated priceHistory,
     * or with status 400 (Bad Request) if the priceHistory is not valid,
     * or with status 500 (Internal Server Error) if the priceHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/price-histories")
    @Timed
    public ResponseEntity<PriceHistory> updatePriceHistory(@Valid @RequestBody PriceHistory priceHistory) throws URISyntaxException {
        log.debug("REST request to update PriceHistory : {}", priceHistory);
        if (priceHistory.getId() == null) {
            return createPriceHistory(priceHistory);
        }
        PriceHistory result = priceHistoryService.save(priceHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, priceHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /price-histories : get all the priceHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of priceHistories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/price-histories")
    @Timed
    public ResponseEntity<List<PriceHistory>> getAllPriceHistories(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PriceHistories");
        Page<PriceHistory> page = priceHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/price-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /price-histories/:id : get the "id" priceHistory.
     *
     * @param id the id of the priceHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priceHistory, or with status 404 (Not Found)
     */
    @GetMapping("/price-histories/{id}")
    @Timed
    public ResponseEntity<PriceHistory> getPriceHistory(@PathVariable Long id) {
        log.debug("REST request to get PriceHistory : {}", id);
        PriceHistory priceHistory = priceHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(priceHistory));
    }

    /**
     * DELETE  /price-histories/:id : delete the "id" priceHistory.
     *
     * @param id the id of the priceHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/price-histories/{id}")
    @Timed
    public ResponseEntity<Void> deletePriceHistory(@PathVariable Long id) {
        log.debug("REST request to delete PriceHistory : {}", id);
        priceHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
