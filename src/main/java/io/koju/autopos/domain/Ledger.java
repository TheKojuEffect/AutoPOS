package io.koju.autopos.domain;

import io.koju.autopos.party.domain.Customer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Ledger.
 */
@Entity
@Table(name = "ledger")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ledger implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @JoinColumn(unique = true)
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
