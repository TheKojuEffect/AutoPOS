package com.kapilkoju.autopos.catalog.domain

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "category")
data class Category(

        @NotNull
        @Size(min = 2, max = 3)
        @Column(name = "short_name", length = 3, nullable = false)
        var shortName: String,

        @NotNull
        @Size(min = 3, max = 50)
        @Column(name = "name", length = 50, nullable = false)
        val name: String

) : AuditableEntity() {


    @PrePersist
    @PreUpdate
    fun capitalizeShortName() {
        this.shortName = shortName.toUpperCase()
    }
}
