package io.koju.autopos.trade.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.trade.domain.Receipt;
import io.koju.autopos.trade.service.ReceiptRepository;
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
 * REST controller for managing Receipt.
 */
@RestController
@RequestMapping("/api")
public class ReceiptResource {

    private final Logger log = LoggerFactory.getLogger(ReceiptResource.class);
        
    @Inject
    private ReceiptRepository receiptRepository;
    
    /**
     * POST  /receipts : Create a new receipt.
     *
     * @param receipt the receipt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new receipt, or with status 400 (Bad Request) if the receipt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
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
     * PUT  /receipts : Updates an existing receipt.
     *
     * @param receipt the receipt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated receipt,
     * or with status 400 (Bad Request) if the receipt is not valid,
     * or with status 500 (Internal Server Error) if the receipt couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
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
     * GET  /receipts : get all the receipts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
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
     * GET  /receipts/:id : get the "id" receipt.
     *
     * @param id the id of the receipt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the receipt, or with status 404 (Not Found)
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
     * DELETE  /receipts/:id : delete the "id" receipt.
     *
     * @param id the id of the receipt to delete
     * @return the ResponseEntity with status 200 (OK)
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
