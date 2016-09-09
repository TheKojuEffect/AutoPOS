package io.koju.autopos.trade.purchase.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import io.koju.autopos.kernel.json.Views;
import io.koju.autopos.party.domain.Vendor;
import io.koju.autopos.trade.domain.Trade;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    @JsonView(Views.Summary.class)
    private Vendor vendor;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PurchaseLine> lines = new ArrayList<>();

    @JsonView(Views.Summary.class)
    public BigDecimal getGrandTotal() {
        return lines.stream()
                    .map(PurchaseLine::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .subtract(getDiscount());
    }

}
