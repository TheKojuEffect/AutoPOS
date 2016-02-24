package io.koju.autopos.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.domain.Brand;
import io.koju.autopos.repository.BrandRepository;
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
 * REST controller for managing Brand.
 */
@RestController
@RequestMapping("/api")
public class BrandResource {

    private final Logger log = LoggerFactory.getLogger(BrandResource.class);
        
    @Inject
    private BrandRepository brandRepository;
    
    /**
     * POST  /brands -> Create a new brand.
     */
    @RequestMapping(value = "/brands",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Brand> createBrand(@Valid @RequestBody Brand brand) throws URISyntaxException {
        log.debug("REST request to save Brand : {}", brand);
        if (brand.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("brand", "idexists", "A new brand cannot already have an ID")).body(null);
        }
        Brand result = brandRepository.save(brand);
        return ResponseEntity.created(new URI("/api/brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("brand", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brands -> Updates an existing brand.
     */
    @RequestMapping(value = "/brands",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Brand> updateBrand(@Valid @RequestBody Brand brand) throws URISyntaxException {
        log.debug("REST request to update Brand : {}", brand);
        if (brand.getId() == null) {
            return createBrand(brand);
        }
        Brand result = brandRepository.save(brand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("brand", brand.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brands -> get all the brands.
     */
    @RequestMapping(value = "/brands",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Brand>> getAllBrands(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Brands");
        Page<Brand> page = brandRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brands");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /brands/:id -> get the "id" brand.
     */
    @RequestMapping(value = "/brands/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Brand> getBrand(@PathVariable Long id) {
        log.debug("REST request to get Brand : {}", id);
        Brand brand = brandRepository.findOne(id);
        return Optional.ofNullable(brand)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /brands/:id -> delete the "id" brand.
     */
    @RequestMapping(value = "/brands/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        log.debug("REST request to delete Brand : {}", id);
        brandRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("brand", id.toString())).build();
    }
}
