package com.kapilkoju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kapilkoju.autopos.domain.PurchaseLine;
import com.kapilkoju.autopos.service.PurchaseLineService;
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
 * REST controller for managing PurchaseLine.
 */
@RestController
@RequestMapping("/api")
public class PurchaseLineResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseLineResource.class);

    private static final String ENTITY_NAME = "purchaseLine";
        
    private final PurchaseLineService purchaseLineService;

    public PurchaseLineResource(PurchaseLineService purchaseLineService) {
        this.purchaseLineService = purchaseLineService;
    }

    /**
     * POST  /purchase-lines : Create a new purchaseLine.
     *
     * @param purchaseLine the purchaseLine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseLine, or with status 400 (Bad Request) if the purchaseLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-lines")
    @Timed
    public ResponseEntity<PurchaseLine> createPurchaseLine(@Valid @RequestBody PurchaseLine purchaseLine) throws URISyntaxException {
        log.debug("REST request to save PurchaseLine : {}", purchaseLine);
        if (purchaseLine.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new purchaseLine cannot already have an ID")).body(null);
        }
        PurchaseLine result = purchaseLineService.save(purchaseLine);
        return ResponseEntity.created(new URI("/api/purchase-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-lines : Updates an existing purchaseLine.
     *
     * @param purchaseLine the purchaseLine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseLine,
     * or with status 400 (Bad Request) if the purchaseLine is not valid,
     * or with status 500 (Internal Server Error) if the purchaseLine couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-lines")
    @Timed
    public ResponseEntity<PurchaseLine> updatePurchaseLine(@Valid @RequestBody PurchaseLine purchaseLine) throws URISyntaxException {
        log.debug("REST request to update PurchaseLine : {}", purchaseLine);
        if (purchaseLine.getId() == null) {
            return createPurchaseLine(purchaseLine);
        }
        PurchaseLine result = purchaseLineService.save(purchaseLine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseLine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-lines : get all the purchaseLines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseLines in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/purchase-lines")
    @Timed
    public ResponseEntity<List<PurchaseLine>> getAllPurchaseLines(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PurchaseLines");
        Page<PurchaseLine> page = purchaseLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-lines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /purchase-lines/:id : get the "id" purchaseLine.
     *
     * @param id the id of the purchaseLine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseLine, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-lines/{id}")
    @Timed
    public ResponseEntity<PurchaseLine> getPurchaseLine(@PathVariable Long id) {
        log.debug("REST request to get PurchaseLine : {}", id);
        PurchaseLine purchaseLine = purchaseLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(purchaseLine));
    }

    /**
     * DELETE  /purchase-lines/:id : delete the "id" purchaseLine.
     *
     * @param id the id of the purchaseLine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-lines/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchaseLine(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseLine : {}", id);
        purchaseLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
