package io.koju.autopos.shared;

import io.koju.autopos.user.domain.User;

import java.time.LocalDateTime;

public interface AuditedEntity {

    User getCreatedBy();

    LocalDateTime getCreatedDate();

    User getLastModifiedBy();

    LocalDateTime getLastModifiedDate();

}

