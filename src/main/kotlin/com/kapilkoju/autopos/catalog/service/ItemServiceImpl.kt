package com.kapilkoju.autopos.catalog.service


import com.kapilkoju.autopos.catalog.domain.Item
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class ItemServiceImpl(private val itemRepo: ItemRepo)
    : ItemService {

    private val log = LoggerFactory.getLogger(ItemServiceImpl::class.java)


    override fun save(item: Item): Item {
        log.debug("Request to save Item : {}", item)
        return itemRepo.save(item)
    }


    @Transactional(readOnly = true)
    override fun findAll(query: String, pageable: Pageable): Page<Item> {
        log.debug("Request to get all Items")

        return if (query.isEmpty()) {
            itemRepo.findAll(pageable)
        } else {
            itemRepo.findByCodeIgnoreCaseContainingOrNameIgnoreCaseContaining(query, query, pageable)
        }
    }

    @Transactional(readOnly = true)
    override fun getItem(id: Long): Item? =
            itemRepo.findOne(id)

    @Transactional(readOnly = true)
    override fun getItemWithDetail(id: Long): Item? =
            itemRepo.findById(id)

    override fun delete(id: Long) {
        log.debug("Request to delete Item : {}", id)
        itemRepo.delete(id)
    }

    override fun adjustQuantity(item: Item, number: Int) {
        val dbItem = itemRepo.findOne(item.getId())
        dbItem.quantity = dbItem.quantity + number
        itemRepo.save(dbItem)
    }

}
