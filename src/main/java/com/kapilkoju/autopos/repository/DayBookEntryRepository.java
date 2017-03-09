package com.kapilkoju.autopos.repository;

import com.kapilkoju.autopos.domain.DayBookEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DayBookEntry entity.
 */
@SuppressWarnings("unused")
public interface DayBookEntryRepository extends JpaRepository<DayBookEntry,Long> {

}
