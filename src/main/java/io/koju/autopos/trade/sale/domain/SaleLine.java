package io.koju.autopos.trade.sale.domain;

import io.koju.autopos.trade.domain.LineItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "sale_line")
@Getter
@Setter
public class SaleLine extends LineItem {

    private static final String ID_SEQ = "sale_line_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Column(name = "buyer")
    private String buyer;

    @Override
    public Long getId() {
        return id;
    }
}


