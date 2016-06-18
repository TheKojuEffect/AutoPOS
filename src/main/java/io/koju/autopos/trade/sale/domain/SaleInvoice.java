package io.koju.autopos.trade.sale.domain;

import com.fasterxml.jackson.annotation.JsonView;
import io.koju.autopos.kernel.web.View;
import io.koju.autopos.trade.domain.Invoice;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "sale_invoice")
@Getter
@Setter
public class SaleInvoice extends Invoice<SaleInvoiceLine> {

    private static final String ID_SEQ = "sale_invoice_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    @JsonView(View.Summary.class)
    private Long id;

    @Enumerated(STRING)
    private Status status;

    @Override
    public Long getId() {
        return id;
    }

    public enum Status {
        PENDING,
        COMPLETED
    }
}
