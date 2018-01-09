package com.kapilkoju.autopos.party.domain

import com.kapilkoju.autopos.kernel.domain.AuditableEntity

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Table(name = "vendor")
@NamedEntityGraph(name = Vendor.Graph.detail,
        attributeNodes = [NamedAttributeNode("phoneNumbers")])
data class Vendor(

        @NotNull
        @Size(min = 2, max = 100)
        @Column(name = "name", length = 100, nullable = false, unique = true)

        val name: String,

        @ElementCollection
        @CollectionTable(name = "vendor_phone_numbers")
        @Column(name = "phone_number")
        val phoneNumbers: List<String> = ArrayList(),

        @Size(max = 250)
        @Column(name = "remarks", length = 250)

        val remarks: String?

) : AuditableEntity() {

    companion object Graph {
        const val detail = "Vendor.detail"
    }

}


