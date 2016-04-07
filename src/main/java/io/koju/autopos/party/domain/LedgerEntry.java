package io.koju.autopos.party.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LedgerEntry.
 */
@Entity
@Table(name = "ledger_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LedgerEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @Size(min = 2, max = 150)
    @Column(name = "particular", length = 150, nullable = false)
    private String particular;

    @Size(min = 1, max = 20)
    @Column(name = "folio", length = 20)
    private String folio;

    @NotNull
    @Min(value = 0)
    @Column(name = "debit", precision=10, scale=2, nullable = false)
    private BigDecimal debit;

    @NotNull
    @Min(value = 0)
    @Column(name = "credit", precision=10, scale=2, nullable = false)
    private BigDecimal credit;

    @NotNull
    @Min(value = 0)
    @Column(name = "balance", precision=10, scale=2, nullable = false)
    private BigDecimal balance;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @ManyToOne
    private Ledger ledger;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
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

    public Ledger getLedger() {
        return ledger;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LedgerEntry ledgerEntry = (LedgerEntry) o;
        if(ledgerEntry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ledgerEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LedgerEntry{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", particular='" + particular + "'" +
            ", folio='" + folio + "'" +
            ", debit='" + debit + "'" +
            ", credit='" + credit + "'" +
            ", balance='" + balance + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
