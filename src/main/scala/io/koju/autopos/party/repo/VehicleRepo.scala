package io.koju.autopos.party.repo

import io.koju.autopos.kernel.service.AuditableBaseRepository
import io.koju.autopos.party.domain.Vehicle

trait VehicleRepo extends AuditableBaseRepository[Vehicle]
  with VehicleQuerydslBinderCustomizer