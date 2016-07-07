package io.koju.autopos.trade.sale.service;

import io.koju.autopos.trade.sale.domain.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {

    Page<Sale> findAll(Pageable pageable);

    Page<Sale> getPendingSales(Pageable pageable);
}
