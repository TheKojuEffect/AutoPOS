package io.koju.autopos.trade.domain;

import io.koju.autopos.catalog.domain.Item;
import io.koju.autopos.kernel.domain.AuditableBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@MappedSuperclass
@Getter
@Setter
public abstract class LineItem extends AuditableBaseEntity {

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @NotNull
    @Min(value = 1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Min(value = 0)
    @Column(name = "rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal rate;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

}
