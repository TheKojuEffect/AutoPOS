package io.koju.autopos.trade.sale.service;

import com.mysema.query.types.expr.BooleanExpression;
import io.koju.autopos.trade.sale.domain.QSale;
import io.koju.autopos.trade.sale.domain.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.koju.autopos.trade.sale.domain.Sale.Status.PENDING;

@Service
class SaleServiceImpl implements SaleService {

    private static final QSale qSale = QSale.sale;

    private final SaleRepository saleRepository;

    @Autowired
    SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Transactional(readOnly = true)
    public Page<Sale> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sale> getPendingSales(Pageable pageable) {
        final BooleanExpression pending = qSale.status.eq(PENDING);
        return saleRepository.findAll(pending, pageable);
    }

}
