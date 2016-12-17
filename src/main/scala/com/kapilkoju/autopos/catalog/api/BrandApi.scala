package com.kapilkoju.autopos.catalog.api

import com.kapilkoju.autopos.catalog.repo.BrandRepo
import com.kapilkoju.autopos.catalog.repo.BrandRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}


@RestController
@RequestMapping(Array("/api/brands"))
class BrandApi(private val brandRepository: BrandRepo)
  extends BaseApi(brandRepository, "brand", "/api/brands")
