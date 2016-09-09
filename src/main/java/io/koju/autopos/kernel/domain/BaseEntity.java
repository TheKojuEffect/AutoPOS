package io.koju.autopos.kernel.domain;

import com.fasterxml.jackson.annotation.JsonView;

public interface BaseEntity {

    @JsonView(Views.Id.class)
    Long getId();

}
