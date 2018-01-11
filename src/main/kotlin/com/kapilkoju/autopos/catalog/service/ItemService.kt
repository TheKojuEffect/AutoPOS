package com.kapilkoju.autopos.catalog.service

import com.kapilkoju.autopos.catalog.domain.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ItemService {

    fun save(item: Item): Item

    fun findAll(query: String, pageable: Pageable): Page<Item>

    fun getItem(id: Long): Item?

    fun getItemWithDetail(id: Long): Item?

    fun delete(id: Long)

    fun adjustQuantity(item: Item, number: Int)

    fun addQuantity(item: Item, number: Int) {
        adjustQuantity(item, number)
    }

    fun subtractQuantity(item: Item, number: Int) {
        adjustQuantity(item, -number)
    }
}
