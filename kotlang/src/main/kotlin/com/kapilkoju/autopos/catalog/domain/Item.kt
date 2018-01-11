package com.kapilkoju.autopos.catalog.domain

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import org.hibernate.annotations.Generated
import org.hibernate.annotations.GenerationTime
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "item")
@NamedEntityGraph(
        name = Item.detail,
        attributeNodes = [
            (NamedAttributeNode("category")),
            NamedAttributeNode("brand"),
            NamedAttributeNode("tags")]
)
data class Item(

        @Column(name = "code", length = 14, nullable = false, insertable = false, updatable = false)
        @Generated(GenerationTime.INSERT)
        val code: String?,

        @NotNull
        @Size(min = 2, max = 50)
        @Column(name = "name", length = 50, nullable = false)
        val name: String,

        @Size(max = 50)
        @Column(name = "part_number", length = 50)
        val partNumber: String?,

        @Size(max = 250)
        @Column(name = "description", length = 250)
        val description: String?,

        @Size(max = 250)
        @Column(name = "location", length = 250)
        val location: String?,

        @Min(0)
        @Column(name = "quantity", nullable = false)
        var quantity: Int = 0,

        @Size(max = 250)
        @Column(name = "remarks", length = 250)
        val remarks: String?,

        @NotNull
        @Min(0)
        @Column(name = "marked_price", precision = 10, scale = 2, nullable = false)
        val markedPrice: BigDecimal,

        @ManyToOne
        @JoinColumn(name = "category_id")
        val category: Category?,

        @ManyToOne
        @JoinColumn(name = "brand_id")
        val brand: Brand?,

        @ManyToMany
        @JoinTable(
                name = "item_tag",
                joinColumns = [JoinColumn(name = "item_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")])
        val tags: Set<Tag>? = HashSet()


) : AuditableEntity() {

    companion object Graph {
        const val detail = "Item.detail"
    }
}
