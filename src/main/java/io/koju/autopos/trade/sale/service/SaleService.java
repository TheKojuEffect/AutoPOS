package io.koju.autopos.trade.sale.service;

import io.koju.autopos.trade.sale.domain.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface SaleService {

    @Transactional(readOnly = true)
    Page<Sale> getSalesWithStatus(Sale.Status status, Pageable pageable);
}
