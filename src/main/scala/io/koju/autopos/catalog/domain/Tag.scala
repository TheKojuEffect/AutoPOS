package io.koju.autopos.catalog.domain

import java.lang.Long
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence._
import javax.validation.constraints.{NotNull, Size}

import io.koju.autopos.kernel.domain.AuditableBaseEntity

import scala.beans.BeanProperty

@Entity
@Table(name = "tag")
class Tag extends AuditableBaseEntity {

  @Id
  @SequenceGenerator(name = "tag_id_seq", sequenceName = "tag_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "tag_id_seq")
  @BeanProperty
  var id: Long = _

  @NotNull
  @Size(min = 2, max = 50)
  @Column(name = "name", length = 50, nullable = false)
  @BeanProperty var name: String = _

}