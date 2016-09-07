package io.koju.autopos.trade.purchase.domain;

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
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "purchase_line")
@Getter
@Setter
public class PurchaseLine extends LineItem {

    private static final String ID_SEQ = "purchase_line_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "purchase_id")
    @JsonBackReference
    private Purchase purchase;

    @Column(name = "buyer")
    private String buyer;

    @Override
    public Long getId() {
        return id;
    }
}


