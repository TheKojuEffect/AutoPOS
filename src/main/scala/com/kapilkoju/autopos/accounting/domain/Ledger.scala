package com.kapilkoju.autopos.accounting.domain

import java.math.BigDecimal
import javax.persistence._
import javax.validation.constraints.{Min, NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.party.domain.Customer

import scala.beans.BeanProperty

@Entity
@Table(name = "ledger")
class Ledger extends AuditableEntity {

  @NotNull
  @Min(value = 0)
  @Column(name = "balance", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var balance: BigDecimal = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty
  var remarks: String = _

  @OneToOne
  @JoinColumn(unique = true)
  @BeanProperty
  var party: Customer = _

}
