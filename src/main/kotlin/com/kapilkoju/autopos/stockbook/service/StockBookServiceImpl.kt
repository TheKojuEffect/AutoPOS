package com.kapilkoju.autopos.stockbook.service

import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.stockbook.domain.StockBookEntry
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional
class StockBookServiceImpl(private val stockBookRepo: StockBookEntryRepo) : StockBookService {

    override fun adjustStock(item: Item, quantity: Int, costPrice: BigDecimal?) {
        val dbStock = stockBookRepo.findOneByItem(item) ?: StockBookEntry(item, 0, costPrice ?: BigDecimal.ZERO)
        dbStock.quantity = dbStock.quantity + quantity
        if (costPrice != null) {
            dbStock.costPrice = costPrice
        }
        stockBookRepo.save(dbStock)
    }
}
