package com.kapilkoju.autopos.service.impl;

import com.kapilkoju.autopos.service.VendorService;
import com.kapilkoju.autopos.domain.Vendor;
import com.kapilkoju.autopos.repository.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Vendor.
 */
@Service
@Transactional
public class VendorServiceImpl implements VendorService{

    private final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);
    
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    /**
     * Save a vendor.
     *
     * @param vendor the entity to save
     * @return the persisted entity
     */
    @Override
    public Vendor save(Vendor vendor) {
        log.debug("Request to save Vendor : {}", vendor);
        Vendor result = vendorRepository.save(vendor);
        return result;
    }

    /**
     *  Get all the vendors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vendor> findAll(Pageable pageable) {
        log.debug("Request to get all Vendors");
        Page<Vendor> result = vendorRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one vendor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Vendor findOne(Long id) {
        log.debug("Request to get Vendor : {}", id);
        Vendor vendor = vendorRepository.findOne(id);
        return vendor;
    }

    /**
     *  Delete the  vendor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vendor : {}", id);
        vendorRepository.delete(id);
    }
}
