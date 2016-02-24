package io.koju.autopos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Receipt.
 */
@Entity
@Table(name = "receipt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Receipt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;
    
    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "received_by", length = 100, nullable = false)
    private String receivedBy;
    
    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;
    
    @ManyToOne
    @JoinColumn(name = "received_from_id")
    private Customer receivedFrom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReceivedBy() {
        return receivedBy;
    }
    
    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Customer getReceivedFrom() {
        return receivedFrom;
    }

    public void setReceivedFrom(Customer customer) {
        this.receivedFrom = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Receipt receipt = (Receipt) o;
        if(receipt.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, receipt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Receipt{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", amount='" + amount + "'" +
            ", receivedBy='" + receivedBy + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
