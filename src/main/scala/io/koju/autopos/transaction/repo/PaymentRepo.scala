package io.koju.autopos.transaction.repo

import java.lang.Long

import io.koju.autopos.transaction.domain.Payment
import org.springframework.data.jpa.repository.JpaRepository

trait PaymentRepo extends JpaRepository[Payment, Long]