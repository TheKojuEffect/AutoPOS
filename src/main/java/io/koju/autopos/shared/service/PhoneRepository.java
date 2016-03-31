package io.koju.autopos.shared.service;

import io.koju.autopos.shared.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone,Long> {

}
