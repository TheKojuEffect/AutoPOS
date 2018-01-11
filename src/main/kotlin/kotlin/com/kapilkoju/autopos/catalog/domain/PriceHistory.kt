package com.kapilkoju.autopos.catalog.domain

import java.math.BigDecimal
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Table(name = "price_history")
data class PriceHistory(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @NotNull
        @Column(name = "date", nullable = false)
        val date: Instant,

        @NotNull
        @Min(value = 0)
        @Column(name = "marked_price", precision = 10, scale = 2, nullable = false)
        val markedPrice: BigDecimal,

        @Size(max = 250)
        @Column(name = "remarks", length = 250)
        val remarks: String?,

        @ManyToOne
        @JoinColumn(name = "item_id")
        val item: Item

)
