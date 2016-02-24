package io.koju.autopos.catalog.service;

import io.koju.autopos.catalog.domain.Category;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Category entity.
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
