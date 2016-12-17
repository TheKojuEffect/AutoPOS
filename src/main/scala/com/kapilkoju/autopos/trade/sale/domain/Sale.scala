package com.kapilkoju.autopos.trade.sale.domain

import java.lang.Long
import java.math.BigDecimal
import java.util
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence._

import com.fasterxml.jackson.annotation.{JsonManagedReference, JsonView}
import com.kapilkoju.autopos.catalog.domain.SaleStatus
import com.kapilkoju.autopos.kernel.json.Views
import com.kapilkoju.autopos.party.domain.Vehicle
import com.kapilkoju.autopos.trade.domain.Trade

import scala.beans.BeanProperty
import scala.collection.JavaConverters._


@Entity
@Table(name = "sale")
class Sale extends Trade {

  @Id
  @SequenceGenerator(name = "sale_id_seq", sequenceName = "sale_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "sale_id_seq")
  @BeanProperty
  var id: Long = _

  @ManyToOne
  @JoinColumn(name = "vehicle_id")
  @BeanProperty
  var vehicle: Vehicle = _

  @Column(name = "buyer")
  @BeanProperty
  var buyer: String = _

  @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER)
  @JsonManagedReference
  @BeanProperty
  var lines: util.List[SaleLine] = new util.ArrayList[SaleLine]

  @Enumerated(EnumType.STRING)
  @JsonView(Array(classOf[Views.Summary]))
  @BeanProperty
  var status: SaleStatus = _

  @JsonView(Array(classOf[Views.Summary]))
  def getClient: String = {
    Option(getVehicle) match {
      case Some(v) => v.getTitle
      case None => buyer
    }
  }

  @JsonView(Array(classOf[Views.Summary]))
  def getGrandTotal: BigDecimal =
    lines.asScala.map(_.getAmount).reduceOption(_.add(_)).map(_.subtract(getDiscount)).getOrElse(BigDecimal.ZERO)

  @PrePersist
  @PreUpdate
  def normalizeClient() {
    if (vehicle != null) {
      buyer = null
    }
  }

}
