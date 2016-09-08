package io.koju.autopos.kernel.domain;

import com.fasterxml.jackson.annotation.JsonView;
import io.koju.autopos.kernel.api.Views;

public interface BaseEntity {

    @JsonView(Views.Id.class)
    Long getId();

}
