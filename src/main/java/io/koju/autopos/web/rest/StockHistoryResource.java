package io.koju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.domain.StockHistory;
import io.koju.autopos.repository.StockHistoryRepository;
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
 * REST controller for managing StockHistory.
 */
@RestController
@RequestMapping("/api")
public class StockHistoryResource {

    private final Logger log = LoggerFactory.getLogger(StockHistoryResource.class);
        
    @Inject
    private StockHistoryRepository stockHistoryRepository;
    
    /**
     * POST  /stock-histories : Create a new stockHistory.
     *
     * @param stockHistory the stockHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockHistory, or with status 400 (Bad Request) if the stockHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stock-histories",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockHistory> createStockHistory(@Valid @RequestBody StockHistory stockHistory) throws URISyntaxException {
        log.debug("REST request to save StockHistory : {}", stockHistory);
        if (stockHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("stockHistory", "idexists", "A new stockHistory cannot already have an ID")).body(null);
        }
        StockHistory result = stockHistoryRepository.save(stockHistory);
        return ResponseEntity.created(new URI("/api/stock-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stockHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-histories : Updates an existing stockHistory.
     *
     * @param stockHistory the stockHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockHistory,
     * or with status 400 (Bad Request) if the stockHistory is not valid,
     * or with status 500 (Internal Server Error) if the stockHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stock-histories",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockHistory> updateStockHistory(@Valid @RequestBody StockHistory stockHistory) throws URISyntaxException {
        log.debug("REST request to update StockHistory : {}", stockHistory);
        if (stockHistory.getId() == null) {
            return createStockHistory(stockHistory);
        }
        StockHistory result = stockHistoryRepository.save(stockHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stockHistory", stockHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-histories : get all the stockHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stockHistories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/stock-histories",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<StockHistory>> getAllStockHistories(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of StockHistories");
        Page<StockHistory> page = stockHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stock-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stock-histories/:id : get the "id" stockHistory.
     *
     * @param id the id of the stockHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockHistory, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/stock-histories/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockHistory> getStockHistory(@PathVariable Long id) {
        log.debug("REST request to get StockHistory : {}", id);
        StockHistory stockHistory = stockHistoryRepository.findOne(id);
        return Optional.ofNullable(stockHistory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stock-histories/:id : delete the "id" stockHistory.
     *
     * @param id the id of the stockHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/stock-histories/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStockHistory(@PathVariable Long id) {
        log.debug("REST request to delete StockHistory : {}", id);
        stockHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stockHistory", id.toString())).build();
    }

}
