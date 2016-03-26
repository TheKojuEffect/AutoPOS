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
 * REST controller for managing BillItem.
 */
@RestController
@RequestMapping("/api")
public class BillItemResource {

    private final Logger log = LoggerFactory.getLogger(BillItemResource.class);
        
    @Inject
    private BillItemRepository billItemRepository;
    
    /**
     * POST  /bill-items : Create a new billItem.
     *
     * @param billItem the billItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billItem, or with status 400 (Bad Request) if the billItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/bill-items",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillItem> createBillItem(@Valid @RequestBody BillItem billItem) throws URISyntaxException {
        log.debug("REST request to save BillItem : {}", billItem);
        if (billItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("billItem", "idexists", "A new billItem cannot already have an ID")).body(null);
        }
        BillItem result = billItemRepository.save(billItem);
        return ResponseEntity.created(new URI("/api/bill-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("billItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bill-items : Updates an existing billItem.
     *
     * @param billItem the billItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billItem,
     * or with status 400 (Bad Request) if the billItem is not valid,
     * or with status 500 (Internal Server Error) if the billItem couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/bill-items",
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
     * GET  /bill-items : get all the billItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of billItems in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/bill-items",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BillItem>> getAllBillItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of BillItems");
        Page<BillItem> page = billItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bill-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bill-items/:id : get the "id" billItem.
     *
     * @param id the id of the billItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billItem, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/bill-items/{id}",
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
     * DELETE  /bill-items/:id : delete the "id" billItem.
     *
     * @param id the id of the billItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/bill-items/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBillItem(@PathVariable Long id) {
        log.debug("REST request to delete BillItem : {}", id);
        billItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("billItem", id.toString())).build();
    }

}
