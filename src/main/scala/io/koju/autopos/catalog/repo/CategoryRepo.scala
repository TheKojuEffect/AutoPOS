package io.koju.autopos.catalog.repo

import io.koju.autopos.catalog.domain.Category
import io.koju.autopos.kernel.service.AuditableBaseRepository

trait CategoryRepo extends AuditableBaseRepository[Category]
