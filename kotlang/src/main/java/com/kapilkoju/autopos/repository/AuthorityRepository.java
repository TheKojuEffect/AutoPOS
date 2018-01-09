package com.kapilkoju.autopos.repository;

import com.kapilkoju.autopos.user.domain.Authority;
import com.kapilkoju.autopos.user.domain.Role;
import com.kapilkoju.autopos.user.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByRole(Role role);
}
