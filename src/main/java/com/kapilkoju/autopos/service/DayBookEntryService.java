package com.kapilkoju.autopos.service;

import com.kapilkoju.autopos.domain.DayBookEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing DayBookEntry.
 */
public interface DayBookEntryService {

    /**
     * Save a dayBookEntry.
     *
     * @param dayBookEntry the entity to save
     * @return the persisted entity
     */
    DayBookEntry save(DayBookEntry dayBookEntry);

    /**
     *  Get all the dayBookEntries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DayBookEntry> findAll(Pageable pageable);

    /**
     *  Get the "id" dayBookEntry.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DayBookEntry findOne(Long id);

    /**
     *  Delete the "id" dayBookEntry.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
