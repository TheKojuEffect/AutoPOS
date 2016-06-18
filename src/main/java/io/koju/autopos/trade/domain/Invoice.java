package io.koju.autopos.trade.domain;

import com.fasterxml.jackson.annotation.JsonView;
import io.koju.autopos.kernel.domain.AuditableBaseEntity;
import io.koju.autopos.kernel.web.View;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
public abstract class Invoice<T extends InvoiceLine> extends AuditableBaseEntity {

    @NotNull
    @Column(name = "date", nullable = false)
    @JsonView(View.Summary.class)
    private LocalDateTime date;

    @OneToMany
    private List<T> lines;

    @NotNull
    @Min(value = 0)
    @Column(name = "sub_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal subTotal;

    @NotNull
    @Min(value = 0)
    @Column(name = "discount", precision = 10, scale = 2, nullable = false)
    private BigDecimal discount;

    @NotNull
    @Min(value = 0)
    @Column(name = "taxable_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal taxableAmount;

    @NotNull
    @Min(value = 0)
    @Column(name = "tax_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @NotNull
    @Min(value = 0)
    @Column(name = "grand_total", precision = 10, scale = 2, nullable = false)
    @JsonView(View.Summary.class)
    private BigDecimal grandTotal;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;
}
