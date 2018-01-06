package com.kapilkoju.autopos.stockbook.domain

import java.math.BigDecimal
import javax.persistence.{Column, Entity, OneToOne, Table}
import javax.validation.constraints.{Min, NotNull, Size}

import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.kernel.domain.AuditableEntity

import scala.beans.BeanProperty

@Entity
@Table(name = "stock_book_entry")
class StockBookEntry extends AuditableEntity {

  @NotNull
  @OneToOne
  @BeanProperty
  var item: Item = _

  @Min(0)
  @Column(name = "quantity", nullable = false)
  @BeanProperty var quantity: Integer = 0

  @NotNull
  @Min(0)
  @Column(name = "cost_price", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var costPrice: BigDecimal = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty
  var remarks: String = _

}
