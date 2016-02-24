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
import org.springframework.web.bind.annotation.*;

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
     * POST  /stockHistorys -> Create a new stockHistory.
     */
    @RequestMapping(value = "/stockHistorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockHistory> createStockHistory(@Valid @RequestBody StockHistory stockHistory) throws URISyntaxException {
        log.debug("REST request to save StockHistory : {}", stockHistory);
        if (stockHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("stockHistory", "idexists", "A new stockHistory cannot already have an ID")).body(null);
        }
        StockHistory result = stockHistoryRepository.save(stockHistory);
        return ResponseEntity.created(new URI("/api/stockHistorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stockHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stockHistorys -> Updates an existing stockHistory.
     */
    @RequestMapping(value = "/stockHistorys",
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
     * GET  /stockHistorys -> get all the stockHistorys.
     */
    @RequestMapping(value = "/stockHistorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<StockHistory>> getAllStockHistorys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of StockHistorys");
        Page<StockHistory> page = stockHistoryRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stockHistorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stockHistorys/:id -> get the "id" stockHistory.
     */
    @RequestMapping(value = "/stockHistorys/{id}",
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
     * DELETE  /stockHistorys/:id -> delete the "id" stockHistory.
     */
    @RequestMapping(value = "/stockHistorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStockHistory(@PathVariable Long id) {
        log.debug("REST request to delete StockHistory : {}", id);
        stockHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stockHistory", id.toString())).build();
    }
}
