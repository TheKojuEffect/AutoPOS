package io.koju.autopos.transaction.domain

import java.lang.Long
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.{JoinColumn, _}
import javax.validation.constraints.{Min, NotNull, Size}

import io.koju.autopos.kernel.domain.AuditableBaseEntity
import io.koju.autopos.party.domain.Vendor

import scala.beans.BeanProperty

@Entity
@Table(name = "payment")
class Payment extends AuditableBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_seq")
  @SequenceGenerator(name = "payment_id_seq", sequenceName = "payment_id_seq", allocationSize = 1)
  @BeanProperty
  var id: Long = _

  @NotNull
  @Column(name = "date_time", nullable = false)
  @BeanProperty
  var dateTime: LocalDateTime = _

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