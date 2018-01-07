package com.kapilkoju.autopos.party.domain

import javax.persistence._
import javax.validation.constraints.{NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableEntity

import scala.beans.BeanProperty


@Entity
@Table(name = "vehicle")
class Vehicle extends AuditableEntity {

  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "number", length = 20, nullable = false, unique = true)
  @BeanProperty
  var number: String = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty
  var remarks: String = _

  @ManyToOne
  @BeanProperty
  var owner: Customer = _

  def getTitle: String = {
    if (owner == null) {
      number
    } else {
      s"[ $number ] ${owner.getName}"
    }
  }
}
