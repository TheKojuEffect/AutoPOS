package io.koju.autopos.party.domain

import java.lang.Long
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence._
import javax.validation.constraints.{NotNull, Size}

import io.koju.autopos.kernel.domain.AuditableBaseEntity

import scala.beans.BeanProperty

@Entity
@Table(name = "customer")
class Customer extends AuditableBaseEntity {

  @Id
  @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "customer_id_seq")
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