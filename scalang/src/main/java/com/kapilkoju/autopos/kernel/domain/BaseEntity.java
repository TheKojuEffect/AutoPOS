package com.kapilkoju.autopos.kernel.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.kapilkoju.autopos.kernel.json.Views;

public interface BaseEntity {

    @JsonView(Views.Id.class)
    Long getId();

}
