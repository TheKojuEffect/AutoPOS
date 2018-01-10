package com.kapilkoju.autopos.catalog.service

import java.lang.Long
import javax.annotation.{Nonnull, Nullable}

import com.kapilkoju.autopos.catalog.domain.Item
import org.springframework.data.domain.{Page, Pageable}

trait ItemService {

  def save(item: Item): Item

  @Nonnull
  def findAll(queryTerm: String, pageable: Pageable): Page[Item]

  @Nullable
  def getItem(id: Long): Item

  @Nullable
  def getItemWithDetail(id: Long): Item

  def delete(id: Long)

  def adjustQuantity(item: Item, number: Integer)

  def addQuantity(item: Item, number: Integer) {
    adjustQuantity(item, number)
  }

  def subtractQuantity(item: Item, number: Integer) {
    adjustQuantity(item, -number)
  }
}
