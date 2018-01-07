package com.kapilkoju.autopos.catalog.api

import com.kapilkoju.autopos.catalog.domain.Brand
import com.kapilkoju.autopos.catalog.service.BrandRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(BrandApi.baseUrl)
class BrandApi(brandRepo: BrandRepo) : BaseApi<Brand>(brandRepo, "brand", BrandApi.baseUrl) {

    companion object {
        const val baseUrl = "/api/brands"
    }
}