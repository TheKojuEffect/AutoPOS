package com.kapilkoju.autopos.accounting.domain

import java.lang.Long
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence._
import javax.validation.constraints.{Min, NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity

import scala.beans.BeanProperty

@Entity
@Table(name = "day_book_entry")
class DayBookEntry extends AuditableBaseEntity {

  @Id
  @SequenceGenerator(name = "day_book_entry_id_seq", sequenceName = "day_book_entry_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "day_book_entry_id_seq")
  @BeanProperty
  var id: Long = _

  @NotNull
  @Column(name = "txn_date", nullable = false)
  @BeanProperty
  var date: LocalDate = _

  @NotNull
  @Min(value = 0)
  @Column(name = "incoming_amount", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var incomingAmount: BigDecimal = _

  @NotNull
  @Min(value = 0)
  @Column(name = "outgoing_amount", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var outgoingAmount: BigDecimal = _

  @NotNull
  @Min(value = 0)
  @Column(name = "misc_expenses", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var miscExpenses: BigDecimal = BigDecimal.ZERO

  @Size(max = 500)
  @Column(name = "remarks", length = 500)
  @BeanProperty
  var remarks: String = _

}
