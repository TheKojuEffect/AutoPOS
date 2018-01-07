package com.kapilkoju.autopos.catalog.domain

import javax.persistence._
import javax.validation.constraints.{NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableEntity

import scala.beans.BeanProperty

@Entity
@Table(name = "tag")
class Tag extends AuditableEntity {

  @NotNull
  @Size(min = 2, max = 50)
  @Column(name = "name", length = 50, nullable = false)
  @BeanProperty var name: String = _

}
