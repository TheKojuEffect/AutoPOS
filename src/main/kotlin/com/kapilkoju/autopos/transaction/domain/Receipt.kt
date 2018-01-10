package com.kapilkoju.autopos.transaction.domain

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.party.domain.Customer
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Table(name = "receipt")
data class Receipt(

        @NotNull
        @Column(name = "date", nullable = false)
        val date: LocalDate,

        @NotNull
        @ManyToOne
        @JoinColumn(name = "received_from_id")
        val receivedFrom: Customer,

        @NotNull
        @Min(value = 0)
        @Column(name = "amount", precision = 10, scale = 2, nullable = false)
        val amount: BigDecimal,

        @Min(1)
        @Column(name = "receipt_number")
        val receiptNumber: Long?,

        @NotNull
        @Size(min = 2, max = 100)
        @Column(name = "received_by", length = 100, nullable = false)
        val receivedBy: String,

        @Size(max = 250)
        @Column(name = "remarks", length = 250)
        val remarks: String?

) : AuditableEntity()
