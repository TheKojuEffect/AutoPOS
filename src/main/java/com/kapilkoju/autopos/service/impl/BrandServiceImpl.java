package com.kapilkoju.autopos.service.impl;

import com.kapilkoju.autopos.service.BrandService;
import com.kapilkoju.autopos.domain.Brand;
import com.kapilkoju.autopos.repository.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Brand.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService{

    private final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);
    
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Save a brand.
     *
     * @param brand the entity to save
     * @return the persisted entity
     */
    @Override
    public Brand save(Brand brand) {
        log.debug("Request to save Brand : {}", brand);
        Brand result = brandRepository.save(brand);
        return result;
    }

    /**
     *  Get all the brands.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Brand> findAll(Pageable pageable) {
        log.debug("Request to get all Brands");
        Page<Brand> result = brandRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one brand by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Brand findOne(Long id) {
        log.debug("Request to get Brand : {}", id);
        Brand brand = brandRepository.findOne(id);
        return brand;
    }

    /**
     *  Delete the  brand by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Brand : {}", id);
        brandRepository.delete(id);
    }
}
