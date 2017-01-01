package com.kapilkoju.autopos.catalog.service

import java.lang.Long

import com.kapilkoju.autopos.catalog.domain.Item
import org.slf4j.LoggerFactory
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemServiceImpl(private val itemRepo: ItemRepo)
  extends ItemService {

  private val log = LoggerFactory.getLogger(classOf[ItemServiceImpl])

  override def save(item: Item): Item = {
    log.debug("Request to save Item : {}", item)
    itemRepo.save(item)
  }

  @Transactional(readOnly = true)
  override def findAll(query: String, pageable: Pageable): Page[Item] = {
    log.debug("Request to get all Items")

    if (query.isEmpty) {
      itemRepo.findAll(pageable)
    } else {
      itemRepo.findByCodeIgnoreCaseContainingOrNameIgnoreCaseContaining(query, query, pageable)
    }
  }

  @Transactional(readOnly = true)
  override def findOne(id: Long): Item = {
    log.debug("Request to get Item : {}", id)
    itemRepo.findOneWithEagerRelationships(id)
  }

  override def delete(id: Long): Unit = {
    log.debug("Request to delete Item : {}", id)
    itemRepo.delete(id)
  }

  override def adjustQuantity(item: Item, number: Integer): Unit = {
    val dbItem = itemRepo.findOne(item.getId)
    dbItem.setQuantity(dbItem.getQuantity + number)
    itemRepo.save(dbItem)
  }
}
