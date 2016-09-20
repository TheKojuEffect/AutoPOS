package io.koju.autopos.transaction.repo

import java.lang.Long

import io.koju.autopos.transaction.domain.Receipt
import org.springframework.data.jpa.repository.JpaRepository

trait ReceiptRepo extends JpaRepository[Receipt, Long]