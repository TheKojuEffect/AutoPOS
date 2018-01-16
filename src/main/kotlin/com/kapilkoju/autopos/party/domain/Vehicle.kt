package com.kapilkoju.autopos.party.domain


import com.kapilkoju.autopos.kernel.domain.AuditableEntity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "vehicle")
data class Vehicle(

        @NotNull
        @Size(min = 1, max = 20)
        @Column(name = "number", length = 20, nullable = false, unique = true)
        val number: String,

        @Size(max = 250)
        @Column(name = "remarks", length = 250)
        val remarks: String?,

        @ManyToOne
        val owner: Customer?

) : AuditableEntity() {

    fun getTitle(): String {
        return if (owner == null) {
            number
        } else {
            "[ $number ] ${owner.name}"
        }
    }


}
