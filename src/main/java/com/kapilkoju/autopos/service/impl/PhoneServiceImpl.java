package com.kapilkoju.autopos.service.impl;

import com.kapilkoju.autopos.service.PhoneService;
import com.kapilkoju.autopos.domain.Phone;
import com.kapilkoju.autopos.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Phone.
 */
@Service
@Transactional
public class PhoneServiceImpl implements PhoneService{

    private final Logger log = LoggerFactory.getLogger(PhoneServiceImpl.class);
    
    private final PhoneRepository phoneRepository;

    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    /**
     * Save a phone.
     *
     * @param phone the entity to save
     * @return the persisted entity
     */
    @Override
    public Phone save(Phone phone) {
        log.debug("Request to save Phone : {}", phone);
        Phone result = phoneRepository.save(phone);
        return result;
    }

    /**
     *  Get all the phones.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Phone> findAll(Pageable pageable) {
        log.debug("Request to get all Phones");
        Page<Phone> result = phoneRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one phone by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Phone findOne(Long id) {
        log.debug("Request to get Phone : {}", id);
        Phone phone = phoneRepository.findOne(id);
        return phone;
    }

    /**
     *  Delete the  phone by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Phone : {}", id);
        phoneRepository.delete(id);
    }
}
