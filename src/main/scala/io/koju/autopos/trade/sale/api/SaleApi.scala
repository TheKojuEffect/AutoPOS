package io.koju.autopos.trade.sale.api

import java.net.URI

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.trade.sale.domain.Sale
import io.koju.autopos.trade.sale.service.SaleService
import io.koju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(Array("/api/sales"))
class SaleApi(private val saleService: SaleService) {

  @GetMapping
  @Timed
  def getAllSales(@RequestParam("status") status: Sale.Status, pageable: Pageable) = {
    val page = saleService.getSalesWithStatus(status, pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sales")
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }

  @PostMapping
  @Timed
  def createNewSale(): ResponseEntity[Sale] = {
    val newSale = saleService.createNewSale()
    ResponseEntity
      .created(new URI(s"/api/sales/${newSale.getId}"))
      .body(newSale)
  }

  @GetMapping(Array("/{id}"))
  @Timed
  def getSale(@PathVariable("id") sale: Sale): ResponseEntity[Sale] = {
    Option(sale) match {
      case Some(s) => ResponseEntity.ok(s)
      case None => new ResponseEntity[Sale](HttpStatus.NOT_FOUND)
    }
  }

}
