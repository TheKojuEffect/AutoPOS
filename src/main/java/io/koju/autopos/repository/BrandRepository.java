package io.koju.autopos.repository;

import io.koju.autopos.domain.Brand;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Brand entity.
 */
public interface BrandRepository extends JpaRepository<Brand,Long> {

}
