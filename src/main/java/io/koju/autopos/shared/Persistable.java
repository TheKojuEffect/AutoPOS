package io.koju.autopos.shared;

import java.io.Serializable;

public interface Persistable<ID extends Serializable> extends Serializable {

    ID getId();

    boolean isNew();
}

