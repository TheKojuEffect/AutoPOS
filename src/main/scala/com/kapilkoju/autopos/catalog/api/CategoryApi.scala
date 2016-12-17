package com.kapilkoju.autopos.catalog.api

import com.kapilkoju.autopos.catalog.repo.CategoryRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}


@RestController
@RequestMapping(Array("/api/categories"))
class CategoryApi(private val categoryRepository: CategoryRepo)
  extends BaseApi(categoryRepository, "category", "/api/categories")

