package com.kapilkoju.autopos.trade.purchase.domain

import java.lang.Long
import java.math.BigDecimal
import java.util
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence._

import com.fasterxml.jackson.annotation.{JsonManagedReference, JsonView}
import com.kapilkoju.autopos.kernel.json.Views.Summary
import com.kapilkoju.autopos.kernel.json.Views
import com.kapilkoju.autopos.party.domain.Vendor
import com.kapilkoju.autopos.trade.domain.Trade

import scala.beans.BeanProperty
import scala.collection.JavaConverters._


@Entity
@Table(name = "purchase")
class Purchase extends Trade {

  @Id
  @SequenceGenerator(name = "purchase_id_seq", sequenceName = "purchase_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "purchase_id_seq")
  @BeanProperty
  var id: Long = _

  @ManyToOne
  @JoinColumn(name = "vendor_id")
  @JsonView(Array(classOf[Summary]))
  @BeanProperty
  var vendor: Vendor = _

  @OneToMany(mappedBy = "purchase", fetch = FetchType.EAGER)
  @JsonManagedReference
  @BeanProperty
  var lines: util.List[PurchaseLine] = new util.ArrayList[PurchaseLine]

  @JsonView(Array(classOf[Views.Summary]))
  def getGrandTotal: BigDecimal = {
    lines.asScala.map(_.getAmount).reduceOption(_.add(_)).map(_.subtract(getDiscount)).getOrElse(BigDecimal.ZERO)
  }

}
