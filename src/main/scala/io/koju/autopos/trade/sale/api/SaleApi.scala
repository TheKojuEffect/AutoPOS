package io.koju.autopos.trade.sale.api

import java.net.URI

import com.codahale.metrics.annotation.Timed
import com.fasterxml.jackson.annotation.JsonView
import io.koju.autopos.catalog.domain.SaleStatus
import io.koju.autopos.kernel.json.Views
import io.koju.autopos.trade.sale.domain.Sale
import io.koju.autopos.trade.sale.repo.SaleLineRepo
import io.koju.autopos.trade.sale.service.SaleService
import io.koju.autopos.web.rest.util.{HeaderUtil, PaginationUtil}
import org.springframework.data.domain.Pageable
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(Array("/api/sales"))
class SaleApi(private val saleService: SaleService,
              private val saleLineRepo: SaleLineRepo) {

  @GetMapping
  @Timed
  @JsonView(Array(classOf[Views.Summary]))
  def getAllSales(@RequestParam("status") status: SaleStatus, pageable: Pageable) = {
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

  @PutMapping(Array("/{id}"))
  @Timed
  def updateSale(@PathVariable("id") id: Long, @RequestBody sale: Sale): ResponseEntity[Sale] = {
    sale.setId(id)
    val updatedSale = saleService.updateSale(sale)
    ResponseEntity.ok
      .headers(HeaderUtil.createEntityUpdateAlert("sale", updatedSale.getId.toString))
      .body(updatedSale)
  }

  @GetMapping(Array("/{id}"))
  @Timed
  def getSale(@PathVariable("id") saleOp: Sale): ResponseEntity[Sale] = {
    Option(saleOp) match {
      case Some(sale) => ResponseEntity.ok(sale)
      case None => new ResponseEntity[Sale](HttpStatus.NOT_FOUND)
    }
  }


}
