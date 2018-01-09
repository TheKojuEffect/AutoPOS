package com.kapilkoju.autopos.trade.domain

import java.math.BigDecimal
import java.time.Instant
import javax.persistence.{Column, MappedSuperclass}
import javax.validation.constraints.{Min, NotNull, Size}

import com.fasterxml.jackson.annotation.JsonView
import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.kernel.json.Views

import scala.beans.BeanProperty

@MappedSuperclass
abstract class Trade extends AuditableEntity {

  @NotNull
  @Column(name = "date", nullable = false)
  @JsonView(Array(classOf[Views.Summary]))
  @BeanProperty
  var date: Instant = _

  @NotNull
  @Min(value = 0)
  @Column(name = "discount", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var discount: BigDecimal = BigDecimal.ZERO

  @Size(min = 1, max = 50)
  @Column(name = "invoice_number", length = 50)
  @JsonView(Array(classOf[Views.Summary]))
  @BeanProperty
  var invoiceNumber: String = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty
  var remarks: String = _
}