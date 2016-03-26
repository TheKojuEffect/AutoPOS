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
 * REST controller for managing PriceHistory.
 */
@RestController
@RequestMapping("/api")
public class PriceHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PriceHistoryResource.class);

    @Inject
    private PriceHistoryRepository priceHistoryRepository;

    /**
     * POST  /price-histories : Create a new priceHistory.
     *
     * @param priceHistory the priceHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priceHistory, or with status 400 (Bad Request) if the priceHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/price-histories",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PriceHistory> createPriceHistory(@Valid @RequestBody PriceHistory priceHistory) throws URISyntaxException {
        log.debug("REST request to save PriceHistory : {}", priceHistory);
        if (priceHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("priceHistory", "idexists", "A new priceHistory cannot already have an ID")).body(null);
        }
        PriceHistory result = priceHistoryRepository.save(priceHistory);
        return ResponseEntity.created(new URI("/api/price-histories/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("priceHistory", result.getId().toString()))
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
    @RequestMapping(value = "/price-histories",
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
     * GET  /price-histories : get all the priceHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of priceHistories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/price-histories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PriceHistory>> getAllPriceHistories(Pageable pageable)
            throws URISyntaxException {
        log.debug("REST request to get a page of PriceHistories");
        Page<PriceHistory> page = priceHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/price-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /price-histories/:id : get the "id" priceHistory.
     *
     * @param id the id of the priceHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priceHistory, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/price-histories/{id}",
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
     * DELETE  /price-histories/:id : delete the "id" priceHistory.
     *
     * @param id the id of the priceHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/price-histories/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePriceHistory(@PathVariable Long id) {
        log.debug("REST request to delete PriceHistory : {}", id);
        priceHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("priceHistory", id.toString())).build();
    }
}
