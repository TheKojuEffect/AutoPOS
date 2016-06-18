package io.koju.autopos.trade.sale.service;

import io.koju.autopos.trade.sale.domain.SaleInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class SaleInvoiceServiceImpl implements SaleInvoiceService {

    private final SaleInvoiceRepository saleInvoiceRepository;

    @Autowired
    SaleInvoiceServiceImpl(SaleInvoiceRepository saleInvoiceRepository) {
        this.saleInvoiceRepository = saleInvoiceRepository;
    }

    @Transactional(readOnly = true)
    public Page<SaleInvoice> findAll(Pageable pageable) {
        return saleInvoiceRepository.findAll(pageable);
    }
}
