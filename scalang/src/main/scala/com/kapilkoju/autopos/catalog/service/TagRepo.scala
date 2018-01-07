package com.kapilkoju.autopos.catalog.service

import com.kapilkoju.autopos.catalog.domain.Tag
import com.kapilkoju.autopos.kernel.service.AuditableRepository

trait TagRepo extends AuditableRepository[Tag]
