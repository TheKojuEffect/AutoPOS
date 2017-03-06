package com.kapilkoju.autopos.repository;

import com.kapilkoju.autopos.domain.Sale;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sale entity.
 */
@SuppressWarnings("unused")
public interface SaleRepository extends JpaRepository<Sale,Long> {

}
