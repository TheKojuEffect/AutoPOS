package io.koju.autopos.repository;

import io.koju.autopos.user.domain.Authority;

import io.koju.autopos.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByRole(Role role);
}
