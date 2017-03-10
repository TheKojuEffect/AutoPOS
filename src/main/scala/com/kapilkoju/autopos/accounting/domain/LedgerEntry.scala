package com.kapilkoju.autopos.accounting.domain

import java.lang.Long
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence._
import javax.validation.constraints.{Min, NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity

import scala.beans.BeanProperty

@Entity
@Table(name = "ledger_entry")
class LedgerEntry extends AuditableBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty var id: Long = _

  @NotNull
  @Column(name = "date", nullable = false)
  @BeanProperty var date: LocalDateTime = _

  @NotNull
  @Size(min = 2, max = 150)
  @Column(name = "particular", length = 150, nullable = false)
  @BeanProperty var particular: String = _

  @Size(min = 1, max = 20)
  @Column(name = "folio", length = 20)
  @BeanProperty var folio: String = _

  @NotNull
  @Min(value = 0)
  @Column(name = "debit", precision = 10, scale = 2, nullable = false)
  @BeanProperty var debit: BigDecimal = _

  @NotNull
  @Min(value = 0)
  @Column(name = "credit", precision = 10, scale = 2, nullable = false)
  @BeanProperty var credit: BigDecimal = _

  @NotNull
  @Min(value = 0)
  @Column(name = "balance", precision = 10, scale = 2, nullable = false)
  @BeanProperty var balance: BigDecimal = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty var remarks: String = _

  @ManyToOne
  @BeanProperty var ledger: Ledger = _

}
