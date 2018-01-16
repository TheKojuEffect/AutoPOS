package com.kapilkoju.autopos.kernel.domain

import com.fasterxml.jackson.annotation.JsonView
import com.kapilkoju.autopos.kernel.json.Views

interface BaseEntity {

    @JsonView(Views.Id::class)
    fun getId(): Long?

}
