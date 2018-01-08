package com.kapilkoju.autopos.accounting.domain

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "day_book_entry")
data class DayBookEntry(

        @NotNull
        @Column(name = "txn_date", nullable = false)
        val date: LocalDate,

        @NotNull
        @Min(value = 0)
        @Column(name = "incoming_amount", precision = 10, scale = 2, nullable = false)
        val incomingAmount: BigDecimal,

        @NotNull
        @Min(value = 0)
        @Column(name = "outgoing_amount", precision = 10, scale = 2, nullable = false)
        val outgoingAmount: BigDecimal,

        @NotNull
        @Min(value = 0)
        @Column(name = "misc_expenses", precision = 10, scale = 2, nullable = false)
        val miscExpenses: BigDecimal = BigDecimal.ZERO,

        @Size(max = 500)
        @Column(name = "remarks", length = 500)
        val remarks: String?

) : AuditableEntity()
