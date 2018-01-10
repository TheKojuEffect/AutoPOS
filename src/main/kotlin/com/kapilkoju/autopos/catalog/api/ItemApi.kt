package com.kapilkoju.autopos.catalog.api

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.catalog.model.CostPriceInfo
import com.kapilkoju.autopos.catalog.service.ItemRepo
import com.kapilkoju.autopos.catalog.service.ItemService
import com.kapilkoju.autopos.kernel.api.CudApi
import com.kapilkoju.autopos.trade.purchase.service.PurchaseService
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(ItemApi.baseUrl)
class ItemApi(itemRepo: ItemRepo,
              private val itemService: ItemService,
              private val purchaseService: PurchaseService)
    : CudApi<Item>(itemRepo, "item", ItemApi.baseUrl) {

    @GetMapping("/{id}")
    @Timed
    fun get(@PathVariable("id") itemId: Long,
            @RequestParam(value = "detail", required = false) detail: Boolean)
            : ResponseEntity<Item> {

        val item =
                if (detail)
                    itemService.getItemWithDetail(itemId)
                else
                    itemService.getItem(itemId)

        return if (item != null) {
            ResponseEntity.ok(item)
        } else {
            ResponseEntity.ok(item)
        }
    }

    @GetMapping
    @Timed
    fun getAll(pageable: Pageable,
               @RequestParam(value = "query", required = false, defaultValue = "")
               query: String): ResponseEntity<List<Item>> {

        val page = itemService.findAll(query, pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, ItemApi.baseUrl)
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    @GetMapping("/{id}/cost_prices")
    fun getCostPriceHistory(@PathVariable("id") itemId: Long): ResponseEntity<List<CostPriceInfo>> {
        val purchaseLines = purchaseService.getPurchaseLines(itemId)
        val costPrices = purchaseLines.map { CostPriceInfo(it) }
        return ResponseEntity.ok(costPrices)
    }

    companion object ItemApi {
        const val baseUrl = "/api/items"
    }

}
