package com.kapilkoju.autopos.stockbook.service

import com.kapilkoju.autopos.catalog.domain.Item
import java.math.BigDecimal

interface StockBookService {

    fun adjustStock(item: Item, quantity: Int, costPrice: BigDecimal? = null)

    fun addStock(item: Item, quantity: Int, costPrice: BigDecimal? = null) {
        adjustStock(item, quantity, costPrice)
    }

    fun subtractStock(item: Item, quantity: Int, costPrice: BigDecimal? = null) {
        adjustStock(item, -quantity, costPrice)
    }
}
