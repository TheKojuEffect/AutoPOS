package io.koju.autopos.trade.sale.api

import java.net.URI
import javax.validation.Valid

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.trade.sale.domain.{Sale, SaleLine}
import io.koju.autopos.trade.sale.repo.SaleLineRepo
import io.koju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(Array("/api/sales/{saleId}/lines"))
class SaleLineApi(private val saleLineRepo: SaleLineRepo) {

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

    saleLine.setSale(sale)
    saleLine.setId(null)
    saleLineRepo.save(saleLine)
    ResponseEntity
      .created(new URI(s"/api/sales/${sale.getId}/lines/${saleLine.getId}"))
      .body(saleLine)
  }

  @PutMapping(Array("/{saleLineId}"))
  @Timed
  def updateSaleLine(@PathVariable("saleId") sale: Sale,
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
