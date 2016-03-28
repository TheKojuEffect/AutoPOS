package io.koju.autopos.accounting.service;

import io.koju.autopos.accounting.domain.DayBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the DayBookEntry entity.
 */
public interface DayBookEntryRepository extends JpaRepository<DayBookEntry,Long> {

}
