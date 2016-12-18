package com.kapilkoju.autopos.party.domain

import java.lang.Long
import javax.persistence.{GeneratedValue, _}
import javax.validation.constraints.{NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity

import scala.beans.BeanProperty

@Entity
@Table(name = "customer")
class Customer extends AuditableBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Long = _

  @NotNull
  @Size(min = 2, max = 100)
  @Column(name = "name", length = 100, nullable = false, unique = true)
  @BeanProperty
  var name: String = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty var remarks: String = _
}
