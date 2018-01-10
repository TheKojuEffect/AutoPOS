package com.kapilkoju.autopos.transaction.domain

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.party.domain.Vendor
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "payment")
data class Payment(
        @NotNull
        @Column(name = "date", nullable = false)
        val date: LocalDate,

        @ManyToOne
        @JoinColumn(name = "paid_to_id")
        @NotNull
        val paidTo: Vendor,

        @NotNull
        @Min(value = 0)
        @Column(name = "amount", precision = 10, scale = 2, nullable = false)
        val amount: BigDecimal,

        @Size(max = 10)
        @Column(name = "receipt_number", length = 10)
        val receiptNumber: String?,

        @NotNull
        @Size(min = 2, max = 100)
        @Column(name = "paid_by", length = 100, nullable = false)
        val paidBy: String,

        @Size(max = 250)
        @Column(name = "remarks", length = 250)
        val remarks: String?

) : AuditableEntity()
