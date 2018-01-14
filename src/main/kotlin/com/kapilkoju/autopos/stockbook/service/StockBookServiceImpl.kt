package com.kapilkoju.autopos.stockbook.service

import com.kapilkoju.autopos.stockbook.domain.StockBookEntry
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StockBookServiceImpl(private val stockBookRepo: StockBookEntryRepo) : StockBookService {

    override fun adjustQuantity(purchaseLine: PurchaseLine, number: Int) {
        val dbStock = stockBookRepo.findOneByItem(purchaseLine.item) ?: StockBookEntry(purchaseLine.item, 0, purchaseLine.rate)
        dbStock.quantity = dbStock.quantity + number
        dbStock.costPrice = purchaseLine.rate
        stockBookRepo.save(dbStock)
    }
}
