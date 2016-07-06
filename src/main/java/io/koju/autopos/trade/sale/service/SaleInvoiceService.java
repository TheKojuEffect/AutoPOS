package io.koju.autopos.trade.sale.service;

import io.koju.autopos.trade.sale.domain.SaleInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleInvoiceService {

    Page<SaleInvoice> findAll(Pageable pageable);

    Page<SaleInvoice> getPendingSaleInvoices(Pageable pageable);
}
