package com.kapilkoju.autopos.stockbook.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StockBookServiceImpl(private val stockBookRepo: StockBookEntryRepo) : StockBookService
