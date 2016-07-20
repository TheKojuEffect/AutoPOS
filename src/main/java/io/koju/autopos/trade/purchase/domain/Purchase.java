package io.koju.autopos.trade.purchase.domain;

import io.koju.autopos.trade.domain.Trade;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "purchase")
@Getter
@Setter
public class Purchase extends Trade {


    private static final String ID_SEQ = "purchase_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    private Long id;

}
