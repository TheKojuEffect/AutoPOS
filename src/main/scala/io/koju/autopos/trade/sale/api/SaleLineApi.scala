package io.koju.autopos.trade.sale.api

import java.net.URI
import javax.validation.Valid

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.trade.sale.domain.{Sale, SaleLine}
import io.koju.autopos.trade.sale.repo.SaleLineRepo
import io.koju.autopos.trade.sale.service.SaleService
import io.koju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(Array("/api/sales/{saleId}/lines"))
class SaleLineApi(private val saleService: SaleService,
                  private val saleLineRepo: SaleLineRepo) {

  @GetMapping(Array("/{saleLineId}"))
  @Timed
  def getSaleLine(@PathVariable("saleId") saleId: Long,
                  @PathVariable("saleLineId") saleLineOp: SaleLine): ResponseEntity[SaleLine] = {
    Option(saleLineOp) match {
      case Some(saleLine)
        if saleLine.getSale.getId == saleId => ResponseEntity.ok(saleLine)
      case _ => new ResponseEntity[SaleLine](HttpStatus.NOT_FOUND)
    }
  }


  @PostMapping
  @Timed
  def createSaleLine(@PathVariable("saleId") sale: Sale,
                     @RequestBody @Valid saleLine: SaleLine): ResponseEntity[SaleLine] = {

    val addedSaleLine = saleService.addSaleLine(sale, saleLine)
    ResponseEntity
      .created(new URI(s"/api/sales/${sale.getId}/lines/${addedSaleLine.getId}"))
      .body(addedSaleLine)
  }

  @PutMapping(Array("/{saleLineId}"))
  @Timed
  def updateSaleLine(@PathVariable("saleId") sale: Sale,
                     @PathVariable("saleLineId") saleLineId: Long,
                     @RequestBody @Valid saleLine: SaleLine): ResponseEntity[SaleLine] = {

    saleLine.setId(saleLineId)
    val updatedSaleLine = saleService.updateSaleLine(sale, saleLine)
    ResponseEntity.ok
      .headers(HeaderUtil.createEntityUpdateAlert("saleLine", updatedSaleLine.getId.toString))
      .body(updatedSaleLine)
  }

  @DeleteMapping(Array("/{saleLineId}"))
  @Timed
  def deleteSaleLine(@PathVariable("saleId") saleId: Long,
                     @PathVariable("saleLineId") saleLineOp: SaleLine): ResponseEntity[Void] = {

    Option(saleLineOp) match {
      case Some(saleLine)
        if saleLine.getSale.getId == saleId => {

        saleService.deleteSaleLine(saleLine)
        ResponseEntity.ok
          .headers(HeaderUtil.createEntityDeletionAlert("saleLine", saleLine.getId.toString)).build
      }
      case _ => new ResponseEntity[Void](HttpStatus.NOT_FOUND)
    }
  }
}
