package com.kapilkoju.autopos.party.domain

import java.lang.Long
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence._
import javax.validation.constraints.{NotNull, Size}

import com.fasterxml.jackson.annotation.JsonView
import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity
import com.kapilkoju.autopos.kernel.json.Views

import scala.beans.BeanProperty

@Entity
@Table(name = "vendor")
class Vendor extends AuditableBaseEntity {

  @Id
  @SequenceGenerator(name = "vendor_id_seq", sequenceName = "vendor_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "vendor_id_seq")
  @BeanProperty
  var id: Long = _

  @NotNull
  @Size(min = 2, max = 100)
  @Column(name = "name", length = 100, nullable = false, unique = true)
  @JsonView(Array(classOf[Views.Summary]))
  @BeanProperty var name: String = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty var remarks: String = _
}
