package com.kapilkoju.autopos.trade.domain

import java.math.BigDecimal
import javax.persistence.{Column, JoinColumn, ManyToOne, MappedSuperclass}
import javax.validation.constraints.{Min, NotNull, Size}

import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity

import scala.beans.BeanProperty

@MappedSuperclass
abstract class LineItem extends AuditableBaseEntity {

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "item_id", nullable = false)
  @BeanProperty
  var item: Item = _

  @NotNull
  @Min(value = 1)
  @Column(name = "quantity", nullable = false)
  @BeanProperty
  var quantity: Integer = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty
  var remarks: String = _

  def getAmount: BigDecimal = getRate.multiply(new BigDecimal(quantity))

  def getRate: BigDecimal
}
