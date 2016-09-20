package io.koju.autopos.trade.purchase.domain

import java.lang.Long
import java.math.BigDecimal
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence._
import javax.validation.constraints.{Min, NotNull}

import com.fasterxml.jackson.annotation.JsonBackReference
import io.koju.autopos.trade.domain.LineItem

import scala.beans.BeanProperty

@Entity
@Table(name = "purchase_line")
class PurchaseLine extends LineItem {

  @Id
  @SequenceGenerator(name = "purchase_line_id_seq", sequenceName = "purchase_line_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "purchase_line_id_seq")
  @BeanProperty
  var id: Long = _

  @NotNull
  @Min(value = 0)
  @Column(name = "rate", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var rate: BigDecimal = _

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "purchase_id")
  @JsonBackReference
  @BeanProperty
  var purchase: Purchase = _
}