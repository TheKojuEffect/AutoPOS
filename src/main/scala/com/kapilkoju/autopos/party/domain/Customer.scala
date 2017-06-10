package com.kapilkoju.autopos.party.domain

import java.util
import javax.persistence._
import javax.validation.constraints.{NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableEntity

import scala.beans.BeanProperty

@Entity
@Table(name = "customer")
@NamedEntityGraph(name = Customer.Graph.detail,
  attributeNodes = Array(new NamedAttributeNode("phoneNumbers")))
class Customer extends AuditableEntity {

  @NotNull
  @Size(min = 2, max = 100)
  @Column(name = "name", length = 100, nullable = false, unique = true)
  @BeanProperty
  var name: String = _

  @ElementCollection
  @CollectionTable(name = "customer_phone_numbers")
  @Column(name = "phone_number")
  @BeanProperty
  var phoneNumbers: util.List[String] = new util.ArrayList[String]()

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty var remarks: String = _

}


object Customer {

  object Graph {
    final val detail = "Customer.detail"
  }

}
