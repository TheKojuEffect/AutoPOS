package io.koju.autopos.shared.domain;

import java.io.Serializable;

public interface IdentifiablePersistable<ID extends Serializable> extends Serializable {

    ID getIdentity();

    boolean isNew();
}

