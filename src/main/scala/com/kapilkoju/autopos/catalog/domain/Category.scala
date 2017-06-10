package com.kapilkoju.autopos.catalog.domain

import javax.persistence._
import javax.validation.constraints.{NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableEntity

import scala.beans.BeanProperty

@Entity
@Table(name = "category")
class Category extends AuditableEntity {

  @NotNull
  @Size(min = 2, max = 3)
  @Column(name = "short_name", length = 3, nullable = false)
  @BeanProperty
  var shortName: String = _

  @NotNull
  @Size(min = 3, max = 50)
  @Column(name = "name", length = 50, nullable = false)
  @BeanProperty
  var name: String = _

  @PrePersist
  @PreUpdate
  def capitalizeShortName() {
    shortName = shortName.toUpperCase
  }
}
