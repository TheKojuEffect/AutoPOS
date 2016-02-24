package io.koju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.domain.Receipt;
import io.koju.autopos.repository.ReceiptRepository;
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
 * REST controller for managing Receipt.
 */
@RestController
@RequestMapping("/api")
public class ReceiptResource {

    private final Logger log = LoggerFactory.getLogger(ReceiptResource.class);
        
    @Inject
    private ReceiptRepository receiptRepository;
    
    /**
     * POST  /receipts -> Create a new receipt.
     */
    @RequestMapping(value = "/receipts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Receipt> createReceipt(@Valid @RequestBody Receipt receipt) throws URISyntaxException {
        log.debug("REST request to save Receipt : {}", receipt);
        if (receipt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("receipt", "idexists", "A new receipt cannot already have an ID")).body(null);
        }
        Receipt result = receiptRepository.save(receipt);
        return ResponseEntity.created(new URI("/api/receipts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("receipt", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /receipts -> Updates an existing receipt.
     */
    @RequestMapping(value = "/receipts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Receipt> updateReceipt(@Valid @RequestBody Receipt receipt) throws URISyntaxException {
        log.debug("REST request to update Receipt : {}", receipt);
        if (receipt.getId() == null) {
            return createReceipt(receipt);
        }
        Receipt result = receiptRepository.save(receipt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("receipt", receipt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /receipts -> get all the receipts.
     */
    @RequestMapping(value = "/receipts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Receipt>> getAllReceipts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Receipts");
        Page<Receipt> page = receiptRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts/:id -> get the "id" receipt.
     */
    @RequestMapping(value = "/receipts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Receipt> getReceipt(@PathVariable Long id) {
        log.debug("REST request to get Receipt : {}", id);
        Receipt receipt = receiptRepository.findOne(id);
        return Optional.ofNullable(receipt)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /receipts/:id -> delete the "id" receipt.
     */
    @RequestMapping(value = "/receipts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteReceipt(@PathVariable Long id) {
        log.debug("REST request to delete Receipt : {}", id);
        receiptRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("receipt", id.toString())).build();
    }
}
