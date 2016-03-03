package io.koju.autopos.shared;

import java.time.LocalDateTime;

public interface AuditableEntity<U> {

    U getCreatedBy();

    LocalDateTime getCreatedDate();

    U getLastModifiedBy();

    LocalDateTime getLastModifiedDate();

}

