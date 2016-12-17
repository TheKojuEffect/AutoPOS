package com.kapilkoju.autopos.catalog.domain

import java.io.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence._
import javax.validation.constraints.{Min, NotNull, Size}

import scala.beans.BeanProperty

@Entity
@Table(name = "price_history") class PriceHistory extends Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  var id: Long = _

  @NotNull
  @Column(name = "date", nullable = false)
  @BeanProperty
  var date: ZonedDateTime = _

  @NotNull
  @Min(value = 0)
  @Column(name = "marked_price", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var markedPrice: BigDecimal = _

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty
  var remarks: String = _

  @ManyToOne
  @JoinColumn(name = "item_id")
  @BeanProperty
  var item: Item = _

}
