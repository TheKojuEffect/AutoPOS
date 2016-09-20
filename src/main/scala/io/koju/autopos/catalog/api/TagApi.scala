package io.koju.autopos.catalog.api

import io.koju.autopos.catalog.repo.TagRepo
import io.koju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/tags"))
class TagApi(private val tagRepository: TagRepo)
  extends BaseApi(tagRepository, "tag", "/api/tags")