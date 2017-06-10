package com.kapilkoju.autopos.party.domain

import java.util
import javax.persistence._
import javax.validation.constraints.{NotNull, Size}

import com.fasterxml.jackson.annotation.JsonView
import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.kernel.json.Views

import scala.beans.BeanProperty

@Entity
@Table(name = "vendor")
@NamedEntityGraph(name = Vendor.Graph.detail,
  attributeNodes = Array(new NamedAttributeNode("phoneNumbers")))
class Vendor extends AuditableEntity {

  @NotNull
  @Size(min = 2, max = 100)
  @Column(name = "name", length = 100, nullable = false, unique = true)
  @JsonView(Array(classOf[Views.Summary]))
  @BeanProperty
  var name: String = _

  @ElementCollection
  @CollectionTable(name = "vendor_phone_numbers")
  @Column(name = "phone_number")
  @BeanProperty
  var phoneNumbers: util.List[String] = new util.ArrayList[String]()

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty
  var remarks: String = _

}


object Vendor {

  object Graph {
    final val detail = "Vendor.detail"
  }

}
