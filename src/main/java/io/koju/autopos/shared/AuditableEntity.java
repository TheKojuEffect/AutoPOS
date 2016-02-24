package io.koju.autopos.shared;

import java.time.LocalDateTime;

public interface AuditableEntity<U>{

    U getCreatedBy();

    void setCreatedBy(final U createdBy);

    LocalDateTime getCreatedDate();

    void setCreatedDate(final LocalDateTime creationDate);

    U getLastModifiedBy();

    void setLastModifiedBy(final U lastModifiedBy);

    LocalDateTime getLastModifiedDate();

    void setLastModifiedDate(final LocalDateTime lastModifiedDate);
}

