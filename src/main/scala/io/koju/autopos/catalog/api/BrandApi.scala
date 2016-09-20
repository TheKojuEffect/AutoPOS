package io.koju.autopos.catalog.api

import io.koju.autopos.catalog.repo.BrandRepo
import io.koju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}


@RestController
@RequestMapping(Array("/api/brands"))
class BrandApi(private val brandRepository: BrandRepo)
  extends BaseApi(brandRepository, "brand", "/api/brands")