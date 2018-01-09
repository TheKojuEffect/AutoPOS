package com.kapilkoju.autopos.stockbook.domain

import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "stock_book_entry")
data class StockBookEntry(

        @NotNull
        @OneToOne
        val item: Item,

        @Min(0)
        @Column(name = "quantity", nullable = false)
        val quantity: Int = 0,

        @NotNull
        @Min(0)
        @Column(name = "cost_price", precision = 10, scale = 2, nullable = false)
        val costPrice: BigDecimal,

        @Size(max = 250)
        @Column(name = "remarks", length = 250)
        val remarks: String?

) : AuditableEntity()
