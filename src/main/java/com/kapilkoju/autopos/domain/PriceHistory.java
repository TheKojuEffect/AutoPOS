package com.kapilkoju.autopos.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PriceHistory.
 */
@Entity
@Table(name = "price_history")
public class PriceHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "marked_price", precision=10, scale=2, nullable = false)
    private BigDecimal markedPrice;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @ManyToOne(optional = false)
    @NotNull
    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public PriceHistory date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public BigDecimal getMarkedPrice() {
        return markedPrice;
    }

    public PriceHistory markedPrice(BigDecimal markedPrice) {
        this.markedPrice = markedPrice;
        return this;
    }

    public void setMarkedPrice(BigDecimal markedPrice) {
        this.markedPrice = markedPrice;
    }

    public String getRemarks() {
        return remarks;
    }

    public PriceHistory remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Item getItem() {
        return item;
    }

    public PriceHistory item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PriceHistory priceHistory = (PriceHistory) o;
        if (priceHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, priceHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PriceHistory{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", markedPrice='" + markedPrice + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
