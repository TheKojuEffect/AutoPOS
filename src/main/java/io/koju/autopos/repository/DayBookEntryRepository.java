package io.koju.autopos.repository;

import io.koju.autopos.domain.DayBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the DayBookEntry entity.
 */
public interface DayBookEntryRepository extends JpaRepository<DayBookEntry,Long> {

}
