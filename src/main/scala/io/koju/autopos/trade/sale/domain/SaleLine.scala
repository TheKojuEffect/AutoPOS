package io.koju.autopos.trade.sale.domain

import java.lang.Long
import java.math.BigDecimal
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence._
import javax.validation.constraints.{Min, NotNull}

import com.fasterxml.jackson.annotation.JsonBackReference
import io.koju.autopos.trade.domain.LineItem

import scala.beans.BeanProperty

@Entity
@Table(name = "sale_line")
class SaleLine extends LineItem {

  @Id
  @SequenceGenerator(name = "sale_line_id_seq", sequenceName = "sale_line_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "sale_line_id_seq")
  @BeanProperty
  var id: Long = _

  @NotNull
  @Min(value = 0)
  @Column(name = "rate", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var rate: BigDecimal = _

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "sale_id")
  @JsonBackReference
  @BeanProperty
  var sale: Sale = _

  @Column(name = "buyer")
  @BeanProperty
  var buyer: String = _
}