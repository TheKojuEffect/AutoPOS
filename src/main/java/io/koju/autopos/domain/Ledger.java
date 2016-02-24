package io.koju.autopos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ledger.
 */
@Entity
@Table(name = "ledger")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ledger implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "balance", precision=10, scale=2, nullable = false)
    private BigDecimal balance;
    
    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;
    
    @OneToOne
    private Customer party;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Customer getParty() {
        return party;
    }

    public void setParty(Customer customer) {
        this.party = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ledger ledger = (Ledger) o;
        if(ledger.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ledger.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ledger{" +
            "id=" + id +
            ", balance='" + balance + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
