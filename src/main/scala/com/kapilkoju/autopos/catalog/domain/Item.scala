package com.kapilkoju.autopos.catalog.domain

import java.lang.Long
import java.math.BigDecimal
import java.util
import javax.persistence._
import javax.validation.constraints.{Min, NotNull, Size}

import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity
import org.hibernate.annotations.{Generated, GenerationTime}

import scala.beans.BeanProperty

@Entity
@Table(name = "item")
class Item extends AuditableBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Long = _

  @Column(name = "code", length = 14, nullable = false, insertable = false, updatable = false)
  @Generated(GenerationTime.INSERT)
  @BeanProperty
  var code: String = _

  @NotNull
  @Size(min = 2, max = 50)
  @Column(name = "name", length = 50, nullable = false)
  @BeanProperty
  var name: String = _

  @Size(max = 50)
  @Column(name = "part_number", length = 50)
  @BeanProperty
  var partNumber: String = _

  @Size(max = 250)
  @Column(name = "description", length = 250)
  @BeanProperty
  var description: String = _

  @Size(max = 250)
  @Column(name = "location", length = 250)
  @BeanProperty
  var location: String = _

  @Min(0)
  @Column(name = "quantity", nullable = false)
  @BeanProperty var quantity: Integer = 0

  @Size(max = 250)
  @Column(name = "remarks", length = 250)
  @BeanProperty
  var remarks: String = _

  @NotNull
  @Min(0)
  @Column(name = "marked_price", precision = 10, scale = 2, nullable = false)
  @BeanProperty
  var markedPrice: BigDecimal = _

  @ManyToOne
  @JoinColumn(name = "category_id")
  @BeanProperty
  var category: Category = _

  @ManyToOne
  @JoinColumn(name = "brand_id")
  @BeanProperty
  var brand: Brand = _

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "item_tag", joinColumns = Array(new JoinColumn(name = "item_id", referencedColumnName = "id")),
    inverseJoinColumns = Array(new JoinColumn(name = "tag_id", referencedColumnName = "id")))
  @BeanProperty
  var tags: java.util.Set[Tag] = new util.HashSet[Tag]

}
