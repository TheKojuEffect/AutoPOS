package com.kapilkoju.autopos.service.impl;

import com.kapilkoju.autopos.service.DayBookEntryService;
import com.kapilkoju.autopos.domain.DayBookEntry;
import com.kapilkoju.autopos.repository.DayBookEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing DayBookEntry.
 */
@Service
@Transactional
public class DayBookEntryServiceImpl implements DayBookEntryService{

    private final Logger log = LoggerFactory.getLogger(DayBookEntryServiceImpl.class);
    
    private final DayBookEntryRepository dayBookEntryRepository;

    public DayBookEntryServiceImpl(DayBookEntryRepository dayBookEntryRepository) {
        this.dayBookEntryRepository = dayBookEntryRepository;
    }

    /**
     * Save a dayBookEntry.
     *
     * @param dayBookEntry the entity to save
     * @return the persisted entity
     */
    @Override
    public DayBookEntry save(DayBookEntry dayBookEntry) {
        log.debug("Request to save DayBookEntry : {}", dayBookEntry);
        DayBookEntry result = dayBookEntryRepository.save(dayBookEntry);
        return result;
    }

    /**
     *  Get all the dayBookEntries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DayBookEntry> findAll(Pageable pageable) {
        log.debug("Request to get all DayBookEntries");
        Page<DayBookEntry> result = dayBookEntryRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one dayBookEntry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DayBookEntry findOne(Long id) {
        log.debug("Request to get DayBookEntry : {}", id);
        DayBookEntry dayBookEntry = dayBookEntryRepository.findOne(id);
        return dayBookEntry;
    }

    /**
     *  Delete the  dayBookEntry by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DayBookEntry : {}", id);
        dayBookEntryRepository.delete(id);
    }
}
