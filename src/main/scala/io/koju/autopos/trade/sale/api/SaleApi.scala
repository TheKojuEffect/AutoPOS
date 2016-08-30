package io.koju.autopos.trade.sale.api

import java.net.URI
import javax.validation.Valid

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.trade.sale.domain.{Sale, SaleLine}
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

  @GetMapping(Array("/{saleId}/lines/{saleLineId}"))
  @Timed
  def getSaleLine(@PathVariable("saleId") saleId: Long,
                  @PathVariable("saleLineId") saleLineOp: SaleLine): ResponseEntity[SaleLine] = {
    Option(saleLineOp) match {
      case Some(saleLine)
        if saleLine.getSale.getId == saleId => ResponseEntity.ok(saleLine)
      case _ => new ResponseEntity[SaleLine](HttpStatus.NOT_FOUND)
    }
  }


  @PostMapping(Array("/{saleId}/lines"))
  @Timed
  def getSaleLine(@PathVariable("saleId") sale: Sale,
                  @RequestBody @Valid saleLine: SaleLine): ResponseEntity[SaleLine] = {

    saleLine.setSale(sale)
    saleLine.setId(null)
    saleLineRepo.save(saleLine)
    ResponseEntity
      .created(new URI(s"/api/sales/${sale.getId}/lines/${saleLine.getId}"))
      .body(saleLine)
  }

  @PutMapping(Array("/{saleId}/lines/{saleLineId}"))
  @Timed
  def getSaleLine(@PathVariable("saleId") sale: Sale,
                  @PathVariable("saleLineId") saleLineId: Long,
                  @RequestBody @Valid saleLine: SaleLine): ResponseEntity[SaleLine] = {

    saleLine.setSale(sale)
    saleLine.setId(saleLineId)
    saleLineRepo.save(saleLine)
    ResponseEntity.ok
      .headers(HeaderUtil.createEntityUpdateAlert("saleLine", saleLine.getId.toString))
      .body(saleLine)
  }
}
