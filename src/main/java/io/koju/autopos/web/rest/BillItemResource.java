package io.koju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.domain.BillItem;
import io.koju.autopos.repository.BillItemRepository;
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
 * REST controller for managing BillItem.
 */
@RestController
@RequestMapping("/api")
public class BillItemResource {

    private final Logger log = LoggerFactory.getLogger(BillItemResource.class);
        
    @Inject
    private BillItemRepository billItemRepository;
    
    /**
     * POST  /billItems -> Create a new billItem.
     */
    @RequestMapping(value = "/billItems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillItem> createBillItem(@Valid @RequestBody BillItem billItem) throws URISyntaxException {
        log.debug("REST request to save BillItem : {}", billItem);
        if (billItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("billItem", "idexists", "A new billItem cannot already have an ID")).body(null);
        }
        BillItem result = billItemRepository.save(billItem);
        return ResponseEntity.created(new URI("/api/billItems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("billItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billItems -> Updates an existing billItem.
     */
    @RequestMapping(value = "/billItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillItem> updateBillItem(@Valid @RequestBody BillItem billItem) throws URISyntaxException {
        log.debug("REST request to update BillItem : {}", billItem);
        if (billItem.getId() == null) {
            return createBillItem(billItem);
        }
        BillItem result = billItemRepository.save(billItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("billItem", billItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billItems -> get all the billItems.
     */
    @RequestMapping(value = "/billItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BillItem>> getAllBillItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of BillItems");
        Page<BillItem> page = billItemRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/billItems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /billItems/:id -> get the "id" billItem.
     */
    @RequestMapping(value = "/billItems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillItem> getBillItem(@PathVariable Long id) {
        log.debug("REST request to get BillItem : {}", id);
        BillItem billItem = billItemRepository.findOne(id);
        return Optional.ofNullable(billItem)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /billItems/:id -> delete the "id" billItem.
     */
    @RequestMapping(value = "/billItems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBillItem(@PathVariable Long id) {
        log.debug("REST request to delete BillItem : {}", id);
        billItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("billItem", id.toString())).build();
    }
}
