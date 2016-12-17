package com.kapilkoju.autopos.kernel.domain;

import com.kapilkoju.autopos.user.domain.User;

import java.time.LocalDateTime;

public interface AuditedEntity {

    User getCreatedBy();

    LocalDateTime getCreatedDate();

    User getLastModifiedBy();

    LocalDateTime getLastModifiedDate();

}

