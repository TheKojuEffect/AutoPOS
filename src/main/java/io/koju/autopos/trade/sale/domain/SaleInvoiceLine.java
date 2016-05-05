package io.koju.autopos.trade.sale.domain;

import io.koju.autopos.trade.domain.InvoiceLine;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "sale_invoice_line")
@Getter
@Setter
public class SaleInvoiceLine extends InvoiceLine {

    private static final String ID_SEQ = "sale_invoice_line_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    private Long id;


    @Override
    public Long getId() {
        return id;
    }
}


