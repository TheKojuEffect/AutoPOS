package com.kapilkoju.autopos.transaction.domain

import java.lang.Long
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence._
import javax.validation.constraints.{Min, NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity
import com.kapilkoju.autopos.party.domain.Customer

import scala.beans.BeanProperty

@Entity
@Table(name = "receipt")
class Receipt extends AuditableBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receipt_id_seq")
  @SequenceGenerator(name = "receipt_id_seq", sequenceName = "receipt_id_seq", allocationSize = 1)
  @BeanProperty
  var id: Long = _

  @NotNull
  @Column(name = "date", nullable = false)
  @BeanProperty
  var date: LocalDate = _

  @ManyToOne
  @JoinColumn(name = "received_from_id")
  @NotNull
  @BeanProperty
  var receivedFrom: Customer = _

  @NotNull
  @Min(value = 0)
  @Column(name = "amount", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var amount: BigDecimal = _

  @Min(1)
  @Column(name = "receipt_number")
  @BeanProperty
  var receiptNumber: Long = _

  @NotNull
  @Size(min = 2, max = 100)
  @Column(name = "received_by", length = 100, nullable = false)
  @BeanProperty
  var receivedBy: String = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty
  var remarks: String = _


}
