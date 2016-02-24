package io.koju.autopos.catalog.service;

import io.koju.autopos.catalog.domain.Tag;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Tag entity.
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

}
