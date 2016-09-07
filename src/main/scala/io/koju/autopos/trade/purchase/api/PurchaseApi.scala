package io.koju.autopos.trade.purchase.api

import java.net.URI

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.trade.purchase.domain.Purchase
import io.koju.autopos.trade.purchase.repo.PurchaseLineRepo
import io.koju.autopos.trade.purchase.service.PurchaseService
import io.koju.autopos.web.rest.util.{HeaderUtil, PaginationUtil}
import org.springframework.data.domain.Pageable
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(Array("/api/purchases"))
class PurchaseApi(private val purchaseService: PurchaseService,
                  private val purchaseLineRepo: PurchaseLineRepo) {

  @GetMapping
  @Timed
  def getAllPurchases(@RequestParam("status") status: Purchase.Status, pageable: Pageable) = {
    val page = purchaseService.getPurchasesWithStatus(status, pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchases")
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }

  @PostMapping
  @Timed
  def createNewPurchase(): ResponseEntity[Purchase] = {
    val newPurchase = purchaseService.createNewPurchase()
    ResponseEntity
      .created(new URI(s"/api/purchases/${newPurchase.getId}"))
      .body(newPurchase)
  }

  @PutMapping(Array("/{id}"))
  @Timed
  def updatePurchase(@PathVariable("id") id: Long, @RequestBody purchase: Purchase): ResponseEntity[Purchase] = {
    purchase.setId(id)
    val updatedPurchase = purchaseService.updatePurchase(purchase)
    ResponseEntity.ok
      .headers(HeaderUtil.createEntityUpdateAlert("purchase", updatedPurchase.getId.toString))
      .body(updatedPurchase)
  }

  @GetMapping(Array("/{id}"))
  @Timed
  def getPurchase(@PathVariable("id") purchaseOp: Purchase): ResponseEntity[Purchase] = {
    Option(purchaseOp) match {
      case Some(purchase) => ResponseEntity.ok(purchase)
      case None => new ResponseEntity[Purchase](HttpStatus.NOT_FOUND)
    }
  }


}
