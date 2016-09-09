package io.koju.autopos.trade.domain;

import io.koju.autopos.kernel.domain.AuditableBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class Trade extends AuditableBaseEntity {

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @NotNull
    @Min(value = 0)
    @Column(name = "discount", precision = 10, scale = 2, nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;
}
