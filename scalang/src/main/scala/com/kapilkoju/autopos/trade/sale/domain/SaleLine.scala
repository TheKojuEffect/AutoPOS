package com.kapilkoju.autopos.trade.sale.domain

import java.math.BigDecimal
import javax.persistence._
import javax.validation.constraints.{Min, NotNull}

import com.fasterxml.jackson.annotation.JsonBackReference
import com.kapilkoju.autopos.trade.domain.LineItem

import scala.beans.BeanProperty

@Entity
@Table(name = "sale_line")
class SaleLine extends LineItem {

  @NotNull
  @Min(value = 0)
  @Column(name = "rate", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var rate: BigDecimal = _

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "sale_id")
  @JsonBackReference
  @BeanProperty
  var sale: Sale = _

  @Column(name = "buyer")
  @BeanProperty
  var buyer: String = _
}
