package com.kapilkoju.autopos.trade.purchase.domain

import java.lang.Long
import java.math.BigDecimal
import javax.persistence._
import javax.validation.constraints.{Min, NotNull}

import com.fasterxml.jackson.annotation.JsonBackReference
import com.kapilkoju.autopos.trade.domain.LineItem

import scala.beans.BeanProperty

@Entity
@Table(name = "purchase_line")
class PurchaseLine extends LineItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Long = _

  @NotNull
  @Min(value = 0)
  @Column(name = "rate", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var rate: BigDecimal = _

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "purchase_id")
  @JsonBackReference
  @BeanProperty
  var purchase: Purchase = _
}
