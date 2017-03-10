package com.kapilkoju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kapilkoju.autopos.domain.SaleLine;
import com.kapilkoju.autopos.service.SaleLineService;
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
 * REST controller for managing SaleLine.
 */
@RestController
@RequestMapping("/api")
public class SaleLineResource {

    private final Logger log = LoggerFactory.getLogger(SaleLineResource.class);

    private static final String ENTITY_NAME = "saleLine";
        
    private final SaleLineService saleLineService;

    public SaleLineResource(SaleLineService saleLineService) {
        this.saleLineService = saleLineService;
    }

    /**
     * POST  /sale-lines : Create a new saleLine.
     *
     * @param saleLine the saleLine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saleLine, or with status 400 (Bad Request) if the saleLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sale-lines")
    @Timed
    public ResponseEntity<SaleLine> createSaleLine(@Valid @RequestBody SaleLine saleLine) throws URISyntaxException {
        log.debug("REST request to save SaleLine : {}", saleLine);
        if (saleLine.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new saleLine cannot already have an ID")).body(null);
        }
        SaleLine result = saleLineService.save(saleLine);
        return ResponseEntity.created(new URI("/api/sale-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sale-lines : Updates an existing saleLine.
     *
     * @param saleLine the saleLine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saleLine,
     * or with status 400 (Bad Request) if the saleLine is not valid,
     * or with status 500 (Internal Server Error) if the saleLine couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sale-lines")
    @Timed
    public ResponseEntity<SaleLine> updateSaleLine(@Valid @RequestBody SaleLine saleLine) throws URISyntaxException {
        log.debug("REST request to update SaleLine : {}", saleLine);
        if (saleLine.getId() == null) {
            return createSaleLine(saleLine);
        }
        SaleLine result = saleLineService.save(saleLine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saleLine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sale-lines : get all the saleLines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of saleLines in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sale-lines")
    @Timed
    public ResponseEntity<List<SaleLine>> getAllSaleLines(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SaleLines");
        Page<SaleLine> page = saleLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sale-lines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sale-lines/:id : get the "id" saleLine.
     *
     * @param id the id of the saleLine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saleLine, or with status 404 (Not Found)
     */
    @GetMapping("/sale-lines/{id}")
    @Timed
    public ResponseEntity<SaleLine> getSaleLine(@PathVariable Long id) {
        log.debug("REST request to get SaleLine : {}", id);
        SaleLine saleLine = saleLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(saleLine));
    }

    /**
     * DELETE  /sale-lines/:id : delete the "id" saleLine.
     *
     * @param id the id of the saleLine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sale-lines/{id}")
    @Timed
    public ResponseEntity<Void> deleteSaleLine(@PathVariable Long id) {
        log.debug("REST request to delete SaleLine : {}", id);
        saleLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
