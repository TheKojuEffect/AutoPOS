package com.kapilkoju.autopos.kernel.domain;

import com.kapilkoju.autopos.user.domain.User;

import java.time.Instant;

public interface AuditedEntity {

    User getCreatedBy();

    Instant getCreatedDate();

    User getLastModifiedBy();

    Instant getLastModifiedDate();

}

