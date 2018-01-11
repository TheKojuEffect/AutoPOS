package com.kapilkoju.autopos.catalog.domain

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "brand")
data class Brand(@NotNull
                 @Size(min = 2, max = 50)
                 @Column(name = "name", length = 50, nullable = false)
                 var name: String
) : AuditableEntity()
