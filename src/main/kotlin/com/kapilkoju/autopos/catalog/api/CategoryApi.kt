package com.kapilkoju.autopos.catalog.api

import com.kapilkoju.autopos.catalog.domain.Category
import com.kapilkoju.autopos.catalog.service.CategoryRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(CategoryApi.baseUrl)
class CategoryApi(categoryRepo: CategoryRepo) : BaseApi<Category>(categoryRepo, "category", CategoryApi.baseUrl) {

  companion object {
    const val baseUrl = "/api/categories"
  }
}