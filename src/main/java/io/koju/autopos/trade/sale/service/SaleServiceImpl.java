package io.koju.autopos.trade.sale.service;

import io.koju.autopos.trade.sale.domain.QSale;
import io.koju.autopos.trade.sale.domain.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class SaleServiceImpl implements SaleService {

    private static final QSale qSale = QSale.sale;

    private final SaleRepository saleRepository;

    @Autowired
    SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sale> getSalesWithStatus(Sale.Status status, Pageable pageable) {
        return saleRepository.findAll(qSale.status.eq(status), pageable);
    }

}
