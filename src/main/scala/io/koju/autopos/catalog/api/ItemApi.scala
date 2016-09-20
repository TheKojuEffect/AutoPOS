package io.koju.autopos.catalog.api

import java.util

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.kernel.api.CrudApi
import io.koju.autopos.catalog.domain.Item
import io.koju.autopos.catalog.service.{ItemRepository, ItemService}
import io.koju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RequestParam, RestController}

@RestController
@RequestMapping(Array("/api/items"))
class ItemApi(itemRepository: ItemRepository,
              itemService: ItemService)
  extends CrudApi(itemRepository, "item", "/api/items") {

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