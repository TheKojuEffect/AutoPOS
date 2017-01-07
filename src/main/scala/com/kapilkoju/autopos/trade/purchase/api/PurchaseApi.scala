package com.kapilkoju.autopos.trade.purchase.api

import java.net.URI
import java.util

import com.codahale.metrics.annotation.Timed
import com.fasterxml.jackson.annotation.JsonView
import com.kapilkoju.autopos.kernel.json.Views
import com.kapilkoju.autopos.trade.purchase.domain.Purchase
import com.kapilkoju.autopos.trade.purchase.service.{PurchaseLineRepo, PurchaseService}
import com.kapilkoju.autopos.web.rest.util.{HeaderUtil, PaginationUtil}
import org.springframework.data.domain.Pageable
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(Array("/api/purchases"))
class PurchaseApi(private val purchaseService: PurchaseService,
                  private val purchaseLineRepo: PurchaseLineRepo) {

  @GetMapping
  @Timed
  @JsonView(Array(classOf[Views.Summary]))
  def getAllPurchases(pageable: Pageable): ResponseEntity[util.List[Purchase]] = {
    val page = purchaseService.getPurchases(pageable)
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
