package io.koju.autopos.trade.sale.api

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.trade.sale.domain.Sale
import io.koju.autopos.trade.sale.service.SaleService
import io.koju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RequestParam, RestController}


@RestController
@RequestMapping(value = Array("/api/sales"))
class SaleApi(private val saleService: SaleService) {

  @GetMapping
  @Timed
  def getAllSales(@RequestParam("status") status: Sale.Status, pageable: Pageable) = {
    val page = saleService.getSalesWithStatus(status, pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sales")
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }

}
