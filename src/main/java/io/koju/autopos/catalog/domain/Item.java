package io.koju.autopos.catalog.domain;

import io.koju.autopos.shared.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Item extends BaseEntity {

    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "code", length = 7, nullable = false)
    private String code;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @NotNull
    @Min(value = 0)
    @Column(name = "marked_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal markedPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "item_tag",
        joinColumns = @JoinColumn(name = "items_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "ID"))
    private Set<Tag> tags = new HashSet<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getMarkedPrice() {
        return markedPrice;
    }

    public void setMarkedPrice(BigDecimal markedPrice) {
        this.markedPrice = markedPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

}
