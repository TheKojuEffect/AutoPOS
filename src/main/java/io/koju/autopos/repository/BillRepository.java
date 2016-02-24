package io.koju.autopos.repository;

import io.koju.autopos.domain.Bill;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bill entity.
 */
public interface BillRepository extends JpaRepository<Bill,Long> {

}
