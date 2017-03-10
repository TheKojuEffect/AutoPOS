package com.kapilkoju.autopos.repository;

import com.kapilkoju.autopos.domain.SaleLine;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SaleLine entity.
 */
@SuppressWarnings("unused")
public interface SaleLineRepository extends JpaRepository<SaleLine,Long> {

}
