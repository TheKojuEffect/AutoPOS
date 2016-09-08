package io.koju.autopos.trade.sale.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

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
    @Min(value = 0)
    @Column(name = "rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal rate;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "sale_id")
    @JsonBackReference
    private Sale sale;

    @Column(name = "buyer")
    private String buyer;

}


