package com.kapilkoju.autopos.catalog.api

import java.util

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.catalog.model.CostPriceInfo
import com.kapilkoju.autopos.catalog.service.{ItemRepo, ItemService}
import com.kapilkoju.autopos.kernel.api.CudApi
import com.kapilkoju.autopos.trade.purchase.service.PurchaseService
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(Array(ItemApi.baseUrl))
class ItemApi(itemRepo: ItemRepo,
              itemService: ItemService,
              purchaseService: PurchaseService)
  extends CudApi(itemRepo, "item", ItemApi.baseUrl) {

  @GetMapping(value = Array("/{id}"))
  @Timed
  def get(@PathVariable("id") itemId: Long,
          @RequestParam(value = "detail", required = false) detail: Boolean): ResponseEntity[Item] = {

    val itemOp =
      if (detail)
        itemService.getItemWithDetail(itemId)
      else
        itemService.getItem(itemId)

    itemOp match {
      case Some(item) => ResponseEntity.ok(item)
      case None => new ResponseEntity(NOT_FOUND)
    }
  }

  @GetMapping
  @Timed
  def getAll(pageable: Pageable,
             @RequestParam(value = "query", required = false, defaultValue = "")
             query: String): ResponseEntity[util.List[Item]] = {

    val page = itemService.findAll(query, pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }

  @GetMapping(value = Array("/{id}/cost_prices"))
  def getCostPriceHistory(@PathVariable("id") itemId: Long): ResponseEntity[List[CostPriceInfo]] = {
    val purchaseLines = purchaseService.getPurchaseLines(itemId)
    val costPrices = purchaseLines.map(line => CostPriceInfo(line))
    ResponseEntity.ok(costPrices)
  }

}

object ItemApi {
  final val baseUrl = "/api/items"
}
