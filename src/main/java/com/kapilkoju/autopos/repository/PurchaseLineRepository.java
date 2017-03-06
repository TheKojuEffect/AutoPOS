package com.kapilkoju.autopos.repository;

import com.kapilkoju.autopos.domain.PurchaseLine;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PurchaseLine entity.
 */
@SuppressWarnings("unused")
public interface PurchaseLineRepository extends JpaRepository<PurchaseLine,Long> {

}
