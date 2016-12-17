package com.kapilkoju.autopos.catalog.api

import com.kapilkoju.autopos.catalog.repo.TagRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/tags"))
class TagApi(private val tagRepository: TagRepo)
  extends BaseApi(tagRepository, "tag", "/api/tags")
