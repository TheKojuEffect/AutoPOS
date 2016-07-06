package io.koju.autopos.trade.sale.service;

import com.mysema.query.types.expr.BooleanExpression;
import io.koju.autopos.trade.sale.domain.QSaleInvoice;
import io.koju.autopos.trade.sale.domain.SaleInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.koju.autopos.trade.sale.domain.SaleInvoice.Status.PENDING;

@Service
class SaleInvoiceServiceImpl implements SaleInvoiceService {

    private static final QSaleInvoice qSaleInvoice = QSaleInvoice.saleInvoice;

    private final SaleInvoiceRepository saleInvoiceRepository;

    @Autowired
    SaleInvoiceServiceImpl(SaleInvoiceRepository saleInvoiceRepository) {
        this.saleInvoiceRepository = saleInvoiceRepository;
    }

    @Transactional(readOnly = true)
    public Page<SaleInvoice> findAll(Pageable pageable) {
        return saleInvoiceRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleInvoice> getPendingSaleInvoices(Pageable pageable) {
        final BooleanExpression pending = qSaleInvoice.status.eq(PENDING);
        return saleInvoiceRepository.findAll(pending, pageable);
    }

}
