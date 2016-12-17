package com.kapilkoju.autopos.catalog.api

import java.util

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.catalog.repo.ItemRepo
import com.kapilkoju.autopos.catalog.service.ItemService
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.catalog.repo.ItemRepo
import com.kapilkoju.autopos.catalog.service.ItemService
import com.kapilkoju.autopos.kernel.api.CrudApi
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RequestParam, RestController}

@RestController
@RequestMapping(Array(ItemApi.baseUrl))
class ItemApi(itemRepository: ItemRepo,
              itemService: ItemService)
  extends CrudApi(itemRepository, "item", ItemApi.baseUrl) {

  @GetMapping
  @Timed
  def getAll(pageable: Pageable,
             @RequestParam(value = "q", required = false, defaultValue = "")
             query: String): ResponseEntity[util.List[Item]] = {

    val page = itemService.findAll(query, pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }

}

object ItemApi {
  final val baseUrl = "/api/items"
}