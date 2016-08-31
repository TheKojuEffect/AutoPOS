package io.koju.autopos.catalog.service

import java.lang.Long

import io.koju.autopos.catalog.domain.{Item, QItem}
import io.koju.autopos.catalog.struct.filter.ItemFilter
import org.slf4j.LoggerFactory
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemServiceImpl(private val itemRepository: ItemRepository)
  extends ItemService {

  private val log = LoggerFactory.getLogger(classOf[ItemServiceImpl])

  override def save(item: Item): Item = {
    log.debug("Request to save Item : {}", item)
    itemRepository.save(item)
  }

  @Transactional(readOnly = true)
  override def findAll(itemFilter: ItemFilter, pageable: Pageable): Page[Item] = {
    log.debug("Request to get all Items")
    val filterPredicate = itemFilter.toQueryDslPredicate(QItem.item)
    itemRepository.findAll(filterPredicate, pageable)
  }

  @Transactional(readOnly = true)
  override def findOne(id: Long): Item = {
    log.debug("Request to get Item : {}", id)
    itemRepository.findOneWithEagerRelationships(id)
  }

  override def delete(id: Long): Unit = {
    log.debug("Request to delete Item : {}", id)
    itemRepository.delete(id)
  }
}
