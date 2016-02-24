package io.koju.autopos.catalog.service;

import io.koju.autopos.catalog.domain.Brand;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Brand entity.
 */
public interface BrandRepository extends JpaRepository<Brand,Long> {

}
