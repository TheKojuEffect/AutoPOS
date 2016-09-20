package io.koju.autopos.catalog.service

import java.lang.Long

import io.koju.autopos.catalog.domain.Item
import org.springframework.data.domain.{Page, Pageable}

trait ItemService {

  def save(item: Item): Item

  def findAll(queryTerm: String, pageable: Pageable): Page[Item]

  def findOne(id: Long): Item

  def delete(id: Long)

  def adjustQuantity(item: Item, number: Integer)

  def addQuantity(item: Item, number: Integer) {
    adjustQuantity(item, number)
  }

  def subtractQuantity(item: Item, number: Integer) {
    adjustQuantity(item, -number)
  }
}