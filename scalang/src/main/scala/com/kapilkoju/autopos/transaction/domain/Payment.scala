package com.kapilkoju.autopos.transaction.domain

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.{JoinColumn, _}
import javax.validation.constraints.{Min, NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.party.domain.Vendor

import scala.beans.BeanProperty

@Entity
@Table(name = "payment")
class Payment extends AuditableEntity {

  @NotNull
  @Column(name = "date", nullable = false)
  @BeanProperty
  var date: LocalDate = _

  @ManyToOne
  @JoinColumn(name = "paid_to_id")
  @NotNull
  @BeanProperty
  var paidTo: Vendor = _

  @NotNull
  @Min(value = 0)
  @Column(name = "amount", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var amount: BigDecimal = _

  @Size(max = 10)
  @Column(name = "receipt_number", length = 10)
  @BeanProperty
  var receiptNumber: String = _

  @NotNull
  @Size(min = 2, max = 100)
  @Column(name = "paid_by", length = 100, nullable = false)
  @BeanProperty
  var paidBy: String = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty
  var remarks: String = _

}
